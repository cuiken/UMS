package com.tp.entity;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="f_topic")
public class Topic extends IdEntity {
    private String name;
    private String value;
    private String description;
    private String icon;
    private Long sort;
    private List<TopicFileLink> topicFileLinkList = Lists.newArrayList();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @OneToMany(mappedBy = "topic",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE},orphanRemoval = true)
    public List<TopicFileLink> getTopicFileLinkList() {
        return topicFileLinkList;
    }

    public void setTopicFileLinkList(List<TopicFileLink> topicFileLinkList) {
        this.topicFileLinkList = topicFileLinkList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
