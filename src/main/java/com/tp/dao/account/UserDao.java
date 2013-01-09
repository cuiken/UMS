package com.tp.dao.account;

import org.springframework.stereotype.Component;

import com.tp.entity.account.User;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class UserDao extends HibernateDao<User, Long> {

}
