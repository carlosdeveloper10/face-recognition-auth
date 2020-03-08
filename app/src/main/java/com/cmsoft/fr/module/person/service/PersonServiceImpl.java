package com.cmsoft.fr.module.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsoft.fr.module.base.service.DtoFactory;
import com.cmsoft.fr.module.base.service.EntityFactory;
import com.cmsoft.fr.module.integration.notification.Notificator;
import com.cmsoft.fr.module.integration.notification.NotificatorFactory;
import com.cmsoft.fr.module.integration.notification.TypeNotificator;
import com.cmsoft.fr.module.media.data.MediaDataFactory;
import com.cmsoft.fr.module.media.data.MediaDataSource;
import com.cmsoft.fr.module.media.data.TypeMediaData;
import com.cmsoft.fr.module.person.dao.EntityExistsException;
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

		checkPersonMandatoryAttributes(personDto);
		person.setPhotoName(generatePersonPhotoName(personDto));
		personDto.setPhotoName(person.getPhotoName());

		if (existUsername(person.getUsername()))
			throw new EntityExistsException("The person already exist in database");

		PersonEntity savedPerson = personDao.save(person);
		this.savePhotoInStorage(personDto);
		this.notifySaving(personDto);
		return (PersonDto) dtoFactory.create(savedPerson);
	}

	private String generatePersonPhotoName(PersonDto person) {
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
	private void checkPersonMandatoryAttributes(PersonDto person) {
		if (person.getUsername() == null || person.getUsername().isEmpty() || person.getPhoneNumber() == null
				|| person.getPhoneNumber().isEmpty() || !ImageUtil.isBase64ImageStructureOk(person.getBase64Photo()))
			throw new IllegalArgumentException("Check person attributes, one or more mandatory attributes are illegal");

	}

	private void savePhotoInStorage(PersonDto savedPerson) {

		MediaDataFactory mediaFactory = new MediaDataFactory();
		MediaDataSource mediaSource = mediaFactory.create(TypeMediaData.AWS_S3_BASIC_BUCKET);
		mediaSource.save(savedPerson.getBase64Photo(), savedPerson.getPhotoName(), "fra-photos");

	}

	private void notifySaving(PersonDto savedPerson) {

		NotificatorFactory notifcatorFactory = new NotificatorFactory();
		Notificator notificator = notifcatorFactory.create(TypeNotificator.AWS_SNS_SMS);
		notificator.notify(savedPerson.getPhoneNumber(),
				"Hi " + savedPerson.getUsername() + ", now you are part of -piece of cake family-");

	}
}
