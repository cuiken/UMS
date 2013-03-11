package com.tp.dao.nav;

import org.springframework.stereotype.Component;

import com.tp.entity.nav.LaunchLog;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class FunBrowserLaunchLogDao extends HibernateDao<LaunchLog, Long> {

}
