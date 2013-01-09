package com.tp.dao.nav;

import org.springframework.stereotype.Component;

import com.tp.entity.nav.Board;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class BoardDao extends HibernateDao<Board, Long> {

}
