package com.cmsoft.fr.module.person.service;

import com.cmsoft.fr.module.base.service.Dto;

public class PersonDto extends Dto {

	private String username;
	
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
