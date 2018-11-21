package com.cmsoft.fr.module.person.service;

import com.cmsoft.fr.module.person.data.entity.Person;

public class PersonServiceImpl implements PersonService {

	@Override
	public Person save(Person person) {
		if (person == null)
			throw new NullPointerException("The entity person must be not null");

		return null;
	}
}
