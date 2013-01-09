package com.tp.entity.nav;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tp.entity.IdEntity;

@Entity
@Table(name = "click_log")
public class ClickLog extends IdEntity {

	private String userId;
	private Long buttonId;
	private Date date;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getButtonId() {
		return buttonId;
	}

	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
