package com.tp.dao.log;

import com.tp.entity.log.LogCountClient2;
import com.tp.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: ken.cui
 * Date: 13-4-17
 * Time: 下午1:09
 */
@Component
public class LogCountClientOriginDao extends HibernateDao<LogCountClient2,Long> {
}
