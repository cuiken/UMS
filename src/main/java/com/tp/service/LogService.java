package com.tp.service;

import com.google.common.collect.Lists;
import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import com.tp.dao.log.*;
import com.tp.entity.log.*;
import com.tp.mapper.JsonMapper;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import com.tp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
@Transactional
public class LogService {

	private static Logger logger = LoggerFactory.getLogger(LogService.class);

	private static final long COUNTER_LOG_HOME = 400L;
	private static final long COUNTER_LOG_CLIENT = 100L;
	private static final long COUNTER_LOG_CONTENT = 100L;

	private List<String> key_log_homes = Lists.newArrayList();

	private List<String> key_log_client = Lists.newArrayList();

	private List<String> key_log_content = Lists.newArrayList();

	private LogFromClientDao logClientDao;
	private LogInHomeDao logHomeDao;
	private LogForContentDao logContentDao;
	private LogForCmccDao logCmccDao;
	private LogForRedirectDao logRedirectDao;
	private LogForPollDao logPollDao;
	private LogCmccResultDao logCmccResultDao;

	private LogCountUnzipDao countUnzipDao;
	private LogCountGetClientDao countGetClientDao;
	private LogCountClientDao countClientDao;
	private LogCountContentDao countContentDao;

	private LogCCInstallPerMarketDao countClientInstallDao;
	private LogCountContentMarketDao countContentPerMarketDao;

	private SpyMemcachedClient memcachedClient;
	private JsonMapper mapper = JsonMapper.buildNormalMapper();

	public void saveCmcc(LogForCmcc entity) {
		logCmccDao.save(entity);
	}

	public void saveRedirect(LogForRedirect entity) {
		logRedirectDao.save(entity);
	}

	public void savePoll(LogForPoll entity) {
		logPollDao.save(entity);
	}

	public void saveCmccResult(LogCmccResult entity) {
		logCmccResultDao.save(entity);
	}

	public void saveClientInstall(LogCountClientInstallPerMarket entity) {
		countClientInstallDao.save(entity);
	}

	public void saveContentPerMarket(LogContentMarket entity) {
		countContentPerMarketDao.save(entity);
	}

	public void saveCountGetClient(LogCountGetClient entity) {
		countGetClientDao.save(entity);
	}

	public void saveCountClient(LogCountClient entity) {
		countClientDao.save(entity);
	}

	public void saveCountContent(LogCountContent entity) {
		countContentDao.save(entity);
	}

	public void saveCountContentUnzip(LogCountUnzip entity) {
		countUnzipDao.save(entity);
	}

	public List<LogInHome> queryLogInHomeByDate(String sdate, String edate) {
		return logHomeDao.queryByDate(sdate, edate);
	}

	public Page<LogCountUnzip> searchContentUnzip(final Page<LogCountUnzip> page, final List<PropertyFilter> filters) {
		return countUnzipDao.findPage(page, filters);
	}

	public Page<LogCountClientInstallPerMarket> searchCountClientInstall(
			final Page<LogCountClientInstallPerMarket> page, final List<PropertyFilter> filters) {
		return countClientInstallDao.findPage(page, filters);
	}



	public Page<LogCountGetClient> searchGetClient(final Page<LogCountGetClient> page,
			final List<PropertyFilter> filters) {
		return countGetClientDao.findPage(page, filters);
	}

	public void saveLogFromClient(LogFromClient entity) {
		String counter = MemcachedObjectType.COUNTER_CLIENT.getPrefix();
		int expTime = MemcachedObjectType.COUNTER_CLIENT.getExpiredTime();
		long count = cachedLogs(counter, expTime, MemcachedObjectType.LOG_CLIENT.getPrefix(), mapper.toJson(entity));
		if (count % COUNTER_LOG_CLIENT == 0) {
			memcachedClient.set(counter, expTime, "0");
			batchLogClient(count);
		}
	}

	public void saveLogContent(LogForContent entity) {
		String counter = MemcachedObjectType.COUNTER_CONTENT.getPrefix();
		int expTime = MemcachedObjectType.COUNTER_CONTENT.getExpiredTime();

		long count = cachedLogs(counter, expTime, MemcachedObjectType.LOG_CONTENT.getPrefix(), mapper.toJson(entity));
		if (count % COUNTER_LOG_CONTENT == 0) {
			memcachedClient.set(counter, expTime, "0");
			batchLogContent(count);
		}
	}

	public void saveLogInHome(LogInHome entity) {

		String counter = MemcachedObjectType.COUNTER_PAGE.getPrefix();
		int expTime = MemcachedObjectType.COUNTER_PAGE.getExpiredTime();

		long count = cachedLogs(counter, expTime, MemcachedObjectType.LOG_PAGE.getPrefix(), mapper.toJson(entity));

		if (count % COUNTER_LOG_HOME == 0) {
			memcachedClient.set(counter, expTime, "0");
			batchLogInHomes(count);
		}
	}

	private long cachedLogs(String counterKey, int exp, String logKey, String logValue) {
		long counter = memcachedClient.getMemcachedClient().incr(counterKey, 1);
		if (counter == -1) {
			memcachedClient.getMemcachedClient().add(counterKey, exp, "1");
			counter = 1;
		}
		memcachedClient.set(logKey + counter, exp, logValue);
		return counter;
	}

	private void batchLogInHomes(long contentSize) {

		Map<String, Object> results = getFromCached(MemcachedObjectType.LOG_PAGE.getPrefix(), contentSize,
				key_log_homes);

        logHomeDao.save(results);
		key_log_homes.clear();
	}

	private void batchLogClient(long contentSize) {
		Map<String, Object> results = getFromCached(MemcachedObjectType.LOG_CLIENT.getPrefix(), contentSize,
				key_log_client);
		int i = 0;
		for (Entry<String, Object> entry : results.entrySet()) {
			String value = (String) entry.getValue();
			LogFromClient entity = mapper.fromJson(value, LogFromClient.class);
			try {
				logClientDao.save(entity);
				if (++i % 20 == 0) {
					logClientDao.flush();
				}
			} catch (Exception e) {
				logger.error(e.getMessage() + " 异常记录为：" + mapper.toJson(entity));
			}

		}
		key_log_client.clear();
	}

	private void batchLogContent(long contentSize) {
		Map<String, Object> results = getFromCached(MemcachedObjectType.LOG_CONTENT.getPrefix(), contentSize,
				key_log_content);
		int i = 0;
		for (Entry<String, Object> entry : results.entrySet()) {
			String value = (String) entry.getValue();
			LogForContent entity = mapper.fromJson(value, LogForContent.class);
			try {
				logContentDao.save(entity);
				if (++i % 20 == 0) {
					logContentDao.flush();
				}
			} catch (Exception e) {
				logger.error(e.getMessage() + " 异常记录为：" + mapper.toJson(entity));
			}

		}
		key_log_content.clear();
	}

	private Map<String, Object> getFromCached(String logKey, long count, List<String> keys) {
		for (int i = 1; i <= count; i++) {
			keys.add(logKey + i);
		}
		return memcachedClient.getBulk(keys);
	}

	public List<LogCountContent> getContentByThemeOrDate(String theme, String date) {
		return countContentDao.getByContentOrDate(theme, date);
	}

	public LogCountClient getLogClientCountByDate(String date) {
		return countClientDao.findUniqueBy("createTime", date);
	}

	public Page<LogCountClient> searchLogCountClient(final Page<LogCountClient> page, final List<PropertyFilter> filters) {
		return countClientDao.findPage(page, filters);
	}

	public Page<LogCountContent> searchLogCountContent(final Page<LogCountContent> page,
			final List<PropertyFilter> filters) {
		return countContentDao.findPage(page, filters);
	}

	/**
	 * 查询用户量
	 */
	public Long countOpenUser(String sdate, String edate) {
		return logClientDao.countUserByDate(sdate, edate);
	}

	/**
	 * 查询总用户量
	 */

	public Long countTotalUser(String edate) {
		return logClientDao.countTotalUser(edate);
	}

	/**
	 * 查询客户端启用次数
	 */
	public Long countUse(String sdate, String edate) {
		return logClientDao.countOpenUseByDate(sdate, edate);
	}

	/**
	 * 查询商店访问用户量
	 */
	public Long countVisitUser(String sdate, String edate) {
		return logHomeDao.countUserInHome(Constants.METHOD_EXECUTE, sdate, edate);
	}

	@Autowired
	public void setLogClientDao(LogFromClientDao logClientDao) {
		this.logClientDao = logClientDao;
	}

	@Autowired
	public void setLogHomeDao(LogInHomeDao logHomeDao) {
		this.logHomeDao = logHomeDao;
	}

	@Autowired
	public void setLogContentDao(LogForContentDao logContentDao) {
		this.logContentDao = logContentDao;
	}

	@Autowired
	public void setLogCmccDao(LogForCmccDao logCmccDao) {
		this.logCmccDao = logCmccDao;
	}

	@Autowired
	public void setLogRedirectDao(LogForRedirectDao logRedirectDao) {
		this.logRedirectDao = logRedirectDao;
	}

	@Autowired
	public void setLogPollDao(LogForPollDao logPollDao) {
		this.logPollDao = logPollDao;
	}

	@Autowired
	public void setLogCmccResultDao(LogCmccResultDao logCmccResultDao) {
		this.logCmccResultDao = logCmccResultDao;
	}

	@Autowired
	public void setCountUnzipDao(LogCountUnzipDao countUnzipDao) {
		this.countUnzipDao = countUnzipDao;
	}

	@Autowired
	public void setCountGetClientDao(LogCountGetClientDao countGetClientDao) {
		this.countGetClientDao = countGetClientDao;
	}

	@Autowired
	public void setCountClientDao(LogCountClientDao countClientDao) {
		this.countClientDao = countClientDao;
	}

	@Autowired
	public void setCountContentDao(LogCountContentDao countContentDao) {
		this.countContentDao = countContentDao;
	}

	@Autowired
	public void setMemcachedClient(SpyMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Autowired
	public void setCountClientInstallDao(LogCCInstallPerMarketDao countClientInstallDao) {
		this.countClientInstallDao = countClientInstallDao;
	}

	@Autowired
	public void setCountContentPerMarketDao(LogCountContentMarketDao countContentPerMarketDao) {
		this.countContentPerMarketDao = countContentPerMarketDao;
	}
}
