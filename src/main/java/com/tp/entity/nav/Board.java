package com.tp.entity.nav;

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
@DiscriminatorValue("board")
public class Board extends TagItem {

	private String color;
	private List<Tag> tags = Lists.newArrayList();
	private List<Navigator> navigators = Lists.newArrayList();
	private List<BoardIcon> icons = Lists.newArrayList();
	private List<HotLink> hotLink = Lists.newArrayList();

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@ManyToMany
	@JoinTable(name = "nav_board_navigator", joinColumns = { @JoinColumn(name = "b_id") }, inverseJoinColumns = { @JoinColumn(name = "n_id") })
	public List<Navigator> getNavigators() {
		return navigators;
	}

	public void setNavigators(List<Navigator> navigators) {
		this.navigators = navigators;
	}

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	public List<BoardIcon> getIcons() {
		return icons;
	}

	public void setIcons(List<BoardIcon> icons) {
		this.icons = icons;
	}

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	public List<HotLink> getHotLink() {
		return hotLink;
	}

	public void setHotLink(List<HotLink> hotLink) {
		this.hotLink = hotLink;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}

}
