package com.flora.domain.db;

import java.io.Serializable;


public class User implements Serializable {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
