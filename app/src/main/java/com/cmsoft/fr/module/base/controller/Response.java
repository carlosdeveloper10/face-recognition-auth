package com.cmsoft.fr.module.base.controller;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the HTTP response for any HTTP request.
 * 
 * 
 * @param <T> Represents the object that was requested.
 * @author carlos
 *
 */
public class Response<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T object;

	private String message;

	private int httpStatusCode;

	@JsonIgnore
	public HttpStatus getHttpStatus() {
		switch (httpStatusCode) {
		case 200:
			return HttpStatus.OK;
		case 201:
			return HttpStatus.CREATED;
		case 500:
			return HttpStatus.INTERNAL_SERVER_ERROR;
		default:
			return HttpStatus.OK;
		}
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
}