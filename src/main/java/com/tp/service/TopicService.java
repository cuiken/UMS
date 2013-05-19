package com.tp.service;

import com.tp.dao.TopicFileLinkDao;
import com.tp.dao.TopicDao;
import com.tp.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional
public class TopicService {
    private TopicDao topicDao;
    private TopicFileLinkDao topicFileLinkDao;

    public Topic getTopic(Long id){
        return topicDao.get(id);
    }

    public void deleteTopic(Long id){
        topicDao.delete(id);
    }

    public void saveTopic(Topic entity){
        topicDao.save(entity);
    }

    public List<Topic> getAllTopics(){
        return topicDao.getAll();
    }
    @Autowired
    public void setTopicDao(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Autowired
    public void setTopicFileLinkDao(TopicFileLinkDao topicFileLinkDao) {
        this.topicFileLinkDao = topicFileLinkDao;
    }
}
