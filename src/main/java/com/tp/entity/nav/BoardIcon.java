package com.tp.entity.nav;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@DiscriminatorValue("bi")
public class BoardIcon extends IconItem {

	private Board board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
