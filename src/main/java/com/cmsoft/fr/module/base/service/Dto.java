package com.cmsoft.fr.module.base.service;

import java.io.Serializable;

public abstract class Dto implements Serializable {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
