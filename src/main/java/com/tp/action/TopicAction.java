package com.tp.action;

import com.google.common.collect.Lists;
import com.tp.dao.HibernateUtils;
import com.tp.entity.Store;
import com.tp.entity.ThemeFile;
import com.tp.entity.Topic;
import com.tp.entity.TopicFileLink;
import com.tp.service.CategoryManager;
import com.tp.service.FileManager;
import com.tp.service.TopicService;
import com.tp.utils.Constants;
import com.tp.utils.FileUtils;
import com.tp.utils.Struts2Utils;
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
@Results({@Result(name=CRUDActionSupport.RELOAD,location = "topic.action",type = "redirect"),
            @Result(name = "topic-manage", location = "topic!manage.action", params = { "id", "${id}" }, type = "redirect")})
public class TopicAction extends CRUDActionSupport<Topic>{

    private Topic entity;
    private Long id;
    private File preview;
    private String previewFileName;
    private List<Topic> topics= Lists.newArrayList();

    private List<Long> checkedFileIds;

    private TopicService topicService;
    private FileManager fileManager;
    private CategoryManager categoryManager;

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

    public String manage() throws Exception{
        topics=topicService.getAllTopics();
        return "manage";
    }

    public String getRemainFile() throws Exception{
        List<ThemeFile> allFiles=Lists.newArrayList();

        Store store=categoryManager.getStoreByValue("lock");
        List<ThemeFile> themeFiles=store.getThemes();
        allFiles.addAll(themeFiles);
        List<ThemeFile>remainFiles=fileManager.getRemainFiles(allFiles,getExistOn());
        String json=fileManager.jsonString(remainFiles);
        Struts2Utils.renderJson(json);
        return null;
    }

    public String getOnShelfFile()throws Exception{

        List<ThemeFile> themeFiles=getExistOn();
        String json=fileManager.jsonString(themeFiles);
        Struts2Utils.renderJson(json);
        return null;
    }

    private List<ThemeFile> getExistOn() throws Exception{
        entity=topicService.getTopic(id);
        List<TopicFileLink> topicFileLinks=entity.getTopicFileLinkList();
        List<ThemeFile> exist=Lists.newArrayList();
        for(TopicFileLink link:topicFileLinks){
            exist.add(link.getThemeFile());
        }
        return exist;
    }

    public String saveFile() throws Exception{
        entity=topicService.getTopic(id);
        topicService.deleteTopicLink(id);
        long i=0;
        for(Long fid:checkedFileIds){
            ThemeFile themeFile=fileManager.getThemeFile(fid);
            TopicFileLink link=new TopicFileLink();
            link.setTopic(entity);
            link.setThemeFile(themeFile);
            link.setSort(++i);
            topicService.saveTopicLink(link);
        }
        addActionMessage("保存成功");
        return "topic-manage";
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

    public Long getId() {
        return id;
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

    public List<Long> getCheckedFileIds() {
        return checkedFileIds;
    }

    public void setCheckedFileIds(List<Long> checkedFileIds) {
        this.checkedFileIds = checkedFileIds;
    }

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    @Autowired
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Autowired
    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
