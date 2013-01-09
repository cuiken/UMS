package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.UserFeedback;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class FeedbackDao extends HibernateDao<UserFeedback, Long> {

}
