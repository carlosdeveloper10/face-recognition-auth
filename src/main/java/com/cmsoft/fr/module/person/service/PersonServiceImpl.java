package com.cmsoft.fr.module.person.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cmsoft.fr.module.person.data.dao.PersonDao;
import com.cmsoft.fr.module.person.data.entity.Person;

public class PersonServiceImpl implements PersonService {

	@Autowired
	@Qualifier("com.cmsoft.fr.module.person.data.dao.PersonDao")
	private PersonDao personDao;

	@Override
	public Person save(Person person) {
		if (person == null)
			throw new NullPointerException("The entity person must be not null");

		checkPersonMandatoryAttributes(person);

		if (existUsername(person.getUsername()))
			throw new EntityExistsException("The person already exist in database");

		return null;
	}

	@Override
	public boolean existUsername(String username) {
		if (username == null)
			throw new NullPointerException("The username can not be not null");

		Person foundPerson = personDao.findByUsername(username);
		if (foundPerson != null)
			return true;

		return false;
	}

	/**
	 * Checks all attributes of person.
	 * 
	 * @throws IllegalArgumentException If some mandatory attribute is illegal.
	 * @param person
	 */
	private void checkPersonMandatoryAttributes(Person person) {
		if (person.getUsername() == null || person.getPhotoName() == null)
			throw new IllegalArgumentException("Check person attributes, one or more mandatory attributes are illegal");
	}
}
