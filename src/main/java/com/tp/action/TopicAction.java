package com.tp.action;

import com.google.common.collect.Lists;
import com.tp.entity.Topic;
import com.tp.service.TopicService;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
@Namespace("/category")
@Results({@Result(name=CRUDActionSupport.RELOAD,location = "topic.action",type = "redirect")})
public class TopicAction extends CRUDActionSupport<Topic>{

    private Topic entity;
    private Long id;
    private File preview;
    private String previewFileName;
    private List<Topic> topics= Lists.newArrayList();

    private TopicService topicService;
    @Override
    public String list() throws Exception {
        topics=topicService.getAllTopics();
        return SUCCESS;
    }

    @Override
    public String input() throws Exception {

        return INPUT;
    }

    @Override
    public String save() throws Exception {
        if(preview!=null){
            String iconPath = FileUtils.getIcon(preview, Constants.CATEGROY_STORAGE, previewFileName);
            entity.setIcon(iconPath);
        }
        topicService.saveTopic(entity);
        addActionMessage("保存成功");
        return RELOAD;
    }

    @Override
    public String delete() throws Exception {
        topicService.deleteTopic(id);
        addActionMessage("删除成功");
        return RELOAD;
    }

    @Override
    protected void prepareModel() throws Exception {
       if(id==null)
       {
           entity=new Topic();
       }else
       {
           entity=topicService.getTopic(id);
       }
    }

    @Override
    public Topic getModel() {
        return entity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPreview(File preview) {
        this.preview = preview;
    }

    public void setPreviewFileName(String previewFileName) {
        this.previewFileName = previewFileName;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }
}
