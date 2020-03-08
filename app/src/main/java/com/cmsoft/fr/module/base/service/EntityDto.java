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

	/**
	 * 
	 */
	private static final long serialVersionUID = -4657506019031285752L;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
