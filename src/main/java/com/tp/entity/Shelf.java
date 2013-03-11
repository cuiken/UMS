package com.tp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.tp.utils.ConvertUtils;

@Entity
@DiscriminatorValue("shelf")
public class Shelf extends CateItem {

	private List<ShelfFileLink> shelfFile = Lists.newArrayList();

	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getCheckedFileIds() {

		List<ShelfFileLink> shelfFiles = this.getShelfFile();
		List<ThemeFile> themes = Lists.newArrayList();
		for (ShelfFileLink sf : shelfFiles) {
			themes.add(sf.getTheme());
		}
		return ConvertUtils.convertElementPropertyToList(themes, "id");
	}

	@OneToMany(mappedBy = "shelf", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	@OrderBy("sort")
	public List<ShelfFileLink> getShelfFile() {
		return shelfFile;
	}

	public void setShelfFile(List<ShelfFileLink> shelfFile) {
		this.shelfFile = shelfFile;
	}

	@Transient
	public Long getCheckedId() {
		if (store == null)
			return 0L;
		else
			return store.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Shelf that = (Shelf) obj;
		return this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	public static enum Type {
		RECOMMEND("recommended", "推荐"),

		HOTTEST("hottest", "最热"),

		GAME("game", "游戏"),

		STAR("star", "星座"),

		NEWEST("newest", "最新");

		public String value;
		public String displayName;

		Type(String value, String displayName) {
			this.value = value;
			this.displayName = displayName;
		}

		public String getValue() {
			return value;
		}

		public String getDisplayName() {
			return displayName;
		}
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
