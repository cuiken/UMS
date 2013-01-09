package com.tp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;

@Entity
@DiscriminatorValue("category")
public class Category extends CateItem {

	private List<ThemeFile> files = Lists.newArrayList();
	private List<CategoryInfo> infos = Lists.newArrayList();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "f_category_file", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = { @JoinColumn(name = "file_id") })
	public List<ThemeFile> getFiles() {
		return files;
	}

	public void setFiles(List<ThemeFile> files) {
		this.files = files;
	}

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	public List<CategoryInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<CategoryInfo> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
