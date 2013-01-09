package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.ClientType;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class ClientTypeDao extends HibernateDao<ClientType, Long> {

}
