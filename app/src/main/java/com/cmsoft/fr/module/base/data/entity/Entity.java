package com.cmsoft.fr.module.base.data.entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

/**
 * Base class for any entity. Classes that inherit from this class are
 * considered as persistent-entity on the database.
 * 
 * @author carlos
 *
 */
@DynamoDBDocument
public abstract class Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
