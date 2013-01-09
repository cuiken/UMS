package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.PollingPreview;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class PollingPreviewDao extends HibernateDao<PollingPreview, Long> {

}
