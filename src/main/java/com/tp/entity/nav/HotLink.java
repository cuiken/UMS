package com.tp.entity.nav;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tp.entity.IdEntity;

@Entity
@Table(name = "nav_hotlink")
public class HotLink extends IdEntity {

	private String name;
	private String addr;
	private String descr;
	private Board board;
	private Tag tag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "board_id")
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
