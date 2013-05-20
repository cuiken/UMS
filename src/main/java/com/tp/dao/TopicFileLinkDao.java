package com.tp.dao;

import com.tp.entity.TopicFileLink;
import com.tp.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TopicFileLinkDao extends HibernateDao<TopicFileLink,Long>{

    private static final String  DELETE_BY_TOPIC="delete from TopicFileLink tfl where tfl.topic.id=?";

    public void delete(Long topicId){
        createQuery(DELETE_BY_TOPIC,topicId).executeUpdate();
    }
}
