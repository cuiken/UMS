package com.tp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: ken.cui
 * Date: 13-5-14
 * Time: 上午9:27
 */
@Entity
@Table(name="f_gender")
public class Gender extends IdEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "name='" + name + '\'' +
                '}';
    }
}
