package com.cmsoft.fr.module.person.service;

import com.cmsoft.fr.module.person.data.entity.Person;

public interface PersonService {

	Person save(Person person);

	/**
	 * Checks if an username is registered in database.
	 * 
	 * @param username that will be checked
	 * @return true if exist otherwise false
	 */
	boolean existUsername(String username);
}
