package com.tp.dao.nav;

import org.springframework.stereotype.Component;

import com.tp.entity.nav.SearchLog;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class SearchLogDao extends HibernateDao<SearchLog, Long> {

}
