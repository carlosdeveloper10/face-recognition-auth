package com.cmsoft.fr.module.person.data.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.cmsoft.fr.module.base.data.entity.Entity;

@javax.persistence.Entity
@Table(name = "person")
public class PersonEntity extends Entity{

	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "photo_name", nullable = false, unique = true)
	private String photoName;
	
	@Column(name = "base64_photo", nullable = false, columnDefinition = "TEXT")
	private String base64Photo;

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

	public String getBase64Photo() {
		return base64Photo;
	}

	public void setBase64Photo(String base64Photo) {
		this.base64Photo = base64Photo;
	}

	
}
