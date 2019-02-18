package com.cmsoft.fr.module.person.service;

public interface PersonService {

	PersonDto save(PersonDto person);

	/**
	 * Checks if an username is registered in database.
	 * 
	 * @param username that will be checked
	 * @return true if exist otherwise false
	 */
	boolean existUsername(String username);
}
