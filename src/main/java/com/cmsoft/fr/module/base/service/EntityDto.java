package com.cmsoft.fr.module.base.service;

import java.io.Serializable;

/**
 * Base class to represent any DTO associated to a entity.
 * 
 * E.g.: PersonEntity -> PersonDto
 * 
 * @author carlos
 *
 */
public abstract class EntityDto implements Serializable {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
