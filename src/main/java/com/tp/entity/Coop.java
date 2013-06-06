package com.tp.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: ken.cui
 * Date: 13-6-6
 * Time: 上午11:11
 */
@Entity
@Table(name="f_coop")
public class Coop extends IdEntity {
    private String name;
    private String value;
    private String channel;

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
