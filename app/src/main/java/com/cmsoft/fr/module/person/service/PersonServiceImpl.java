package com.cmsoft.fr.module.person.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsoft.fr.module.base.service.DtoFactory;
import com.cmsoft.fr.module.base.service.EntityFactory;
import com.cmsoft.fr.module.person.data.dao.PersonDao;
import com.cmsoft.fr.module.person.data.entity.PersonEntity;
import com.cmsoft.fr.module.recognition.image.ImageUtil;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonDao personDao;
	private DtoFactory dtoFactory;
	private EntityFactory entityFactory;

	/**
	 * Use this construct if you want to do some task of CRUD for person.
	 * 
	 * @param personDao
	 * @param dtoFactory
	 * @param entityFactory
	 */
	@Autowired
	public PersonServiceImpl(PersonDao personDao, DtoFactory dtoFactory, EntityFactory entityFactory) {
		this.personDao = personDao;
		this.dtoFactory = dtoFactory;
		this.entityFactory = entityFactory;
	}

	@Override
	public PersonDto save(PersonDto personDto) {
		if (personDto == null)
			throw new NullPointerException("The entity person must be not null");

		PersonEntity person = (PersonEntity) entityFactory.create(personDto);

		checkPersonMandatoryAttributes(person);
		person.setPhotoName(generatePersonPhotoName(person));

		if (existUsername(person.getUsername()))
			throw new EntityExistsException("The person already exist in database");

		PersonEntity savedPerson = personDao.save(person);
		return (PersonDto) dtoFactory.create(savedPerson);
	}

	private String generatePersonPhotoName(PersonEntity person) {
		String photoExtension = ImageUtil.getBase64ImageExtension(person.getBase64Photo());
		return person.getUsername() + "." + photoExtension;
	}

	@Override
	public boolean existUsername(String username) {
		if (username == null)
			throw new NullPointerException("The username can not be not null");

		PersonEntity foundPerson = personDao.findByUsername(username);
		return (foundPerson != null);
	}

	/**
	 * Checks all attributes of person.
	 * 
	 * @throws IllegalArgumentException If some mandatory attribute is illegal.
	 * @param person
	 */
	private void checkPersonMandatoryAttributes(PersonEntity person) {
		if (person.getUsername() == null || person.getUsername().isEmpty()
				|| !ImageUtil.isBase64ImageStructureOk(person.getBase64Photo()))
			throw new IllegalArgumentException("Check person attributes, one or more mandatory attributes are illegal");

	}
}
