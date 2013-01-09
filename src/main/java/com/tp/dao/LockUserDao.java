package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.LockUser;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LockUserDao extends HibernateDao<LockUser, Long> {

}
