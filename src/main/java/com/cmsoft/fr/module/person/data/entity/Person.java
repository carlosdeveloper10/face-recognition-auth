package com.cmsoft.fr.module.person.data.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.cmsoft.fr.module.base.data.entity.Entity;

@javax.persistence.Entity
@Table(name = "person")
public class Person extends Entity{

	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "photo_name", nullable = false, unique = true)
	private String photoName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
}
