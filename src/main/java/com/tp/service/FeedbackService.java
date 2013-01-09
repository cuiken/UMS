package com.tp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tp.dao.FeedbackDao;
import com.tp.entity.UserFeedback;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;

@Component
@Transactional
public class FeedbackService {

	private FeedbackDao feedbackDao;

	public void save(UserFeedback entity) {
		feedbackDao.save(entity);
	}

	public UserFeedback get(Long id) {
		return feedbackDao.get(id);
	}
	
	public void delete(Long id){
		feedbackDao.delete(id);
	}

	public Page<UserFeedback> searchFeedback(final Page<UserFeedback> page, final List<PropertyFilter> filters) {
		return feedbackDao.findPage(page, filters);
	}

	@Autowired
	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}
}
