package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.Advertisement;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class AdvertisementDao extends HibernateDao<Advertisement, Long> {

}
