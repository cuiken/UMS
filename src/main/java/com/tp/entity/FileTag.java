package com.tp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: ken.cui
 * Date: 13-5-14
 * Time: 上午9:27
 */
@Entity
@Table(name="f_tag")
public class FileTag extends IdEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
