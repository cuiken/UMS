package com.tp.dao.log;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.tp.dao.ICacheSaver;
import com.tp.entity.log.LogForCmcc;
import com.tp.orm.hibernate.HibernateDao;
import com.tp.utils.Constants;
import com.tp.utils.WriteSychronziedListCache;

@Component
public class LogForCmccDao extends HibernateDao<LogForCmcc, Long> implements ICacheSaver<LogForCmcc> {

	int batchSize = 20;

	WriteSychronziedListCache<LogForCmcc> cache;

	/*
	 * 
	 */
	@PostConstruct
	void init() {
		cache = new WriteSychronziedListCache<LogForCmcc>();
		cache.setCommitDuration(Long.parseLong(Constants.CMCC_LOG_COMMIT_DURATION));
		cache.setSaver(this);
		batchSize = Integer.parseInt(Constants.CMCC_LOG_COMMIT_BATCHSIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tp.orm.hibernate.SimpleHibernateDao#save(java.lang.Object)
	 * 
	 * 重写save方法，把每次用户点击详情页面产生的log写到全局cache中。这样request
	 * 级别的log就合并到application级别，累计到一定数量再写入数据库，以减轻数据库 频繁write操作的压力。
	 */
	@Override
	public void save(LogForCmcc entity) {
		super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tp.dao.ICacheSaver#saveList(java.util.List)
	 * 
	 * 批量插入移动详情点击记录。此处要保证for循环中的session.save()提交的
	 * insert语句会被hibernate自动拼成一句insert，以实现性能优化。
	 * 
	 * 如果save出错则需要回滚，并抛出异常通知调用的函数不要清空缓存，现有 数据留在下一个log的check-point再尝试写入数据库。
	 */
	@Override
	public void saveList(List<LogForCmcc> e) throws Exception {
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			int i = 0;

			// 为防止爆内存，一次性只提交最多batchSize条insert
			for (; i < e.size(); i++) {
				session.save(e.get(i));
				if (i % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}

			for (; i < e.size(); i++) {
				session.save(e.get(i));
			}
			session.flush();
			session.clear();

			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception ex2) {
				}
			}
		}

	}
}
