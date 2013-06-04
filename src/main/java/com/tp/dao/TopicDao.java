package com.tp.dao;

import com.tp.entity.Topic;
import com.tp.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TopicDao extends HibernateDao<Topic,Long> {
    private static final String QUERY_BY_SORT="select t from Topic t order by t.sort asc";

    @Override
    public List<Topic> getAll(){
        return createQuery(QUERY_BY_SORT).list();
    }
}
