package com.tp.dao.nav;

import org.springframework.stereotype.Component;

import com.tp.entity.nav.Navigator;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class NavigatorDao extends HibernateDao<Navigator, Long> {

}
