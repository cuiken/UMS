package com.tp.dao;

import org.springframework.stereotype.Component;

import com.tp.entity.Market;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class MarketDao extends HibernateDao<Market, Long> {

}
