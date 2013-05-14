package com.tp.dao;

import com.tp.entity.Gender;
import com.tp.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: ken.cui
 * Date: 13-5-14
 * Time: 上午10:49
 */
@Component
public class GenderDao extends HibernateDao<Gender, Long> {
}
