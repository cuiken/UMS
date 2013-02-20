package com.tp.dao.log;

import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCountClientInstallWithContent;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCCInstallWithContentDao extends HibernateDao<LogCountClientInstallWithContent, Long> {

}
