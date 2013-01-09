package com.tp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FileDTO {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}

}
