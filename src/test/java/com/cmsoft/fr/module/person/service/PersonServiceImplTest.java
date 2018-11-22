package com.cmsoft.fr.module.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmsoft.fr.module.base.service.DtoFactory;
import com.cmsoft.fr.module.base.service.DtoFactoryImpl;
import com.cmsoft.fr.module.base.service.EntityFactory;
import com.cmsoft.fr.module.base.service.EntityFactoryImpl;
import com.cmsoft.fr.module.person.data.dao.PersonDao;
import com.cmsoft.fr.module.person.data.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

	@Mock
	private PersonDao personDao;

	private PersonService personService;

	@Before
	public void setup() {
		DtoFactory dtoFactory = new DtoFactoryImpl();
		EntityFactory entityFactory = new EntityFactoryImpl();
		personService = new PersonServiceImpl(personDao, dtoFactory, entityFactory);
	}

	// -------- TDD for save(Person person) -------//
	@Test
	public void whenPersonIsNullThenThrownNullPointerException() {

		assertThatThrownBy(() -> {
			personService.save(null);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The entity person must be not null");
	}

	@Test
	public void whenAMandatoryPersonAttributeIsIllegalThenThrownIllegalArgumentException() {

		PersonDto personToSave = new PersonDto();

		assertThatThrownBy(() -> {
			personService.save(personToSave);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check person attributes, one or more mandatory attributes are illegal");
	}

	@Test
	public void whenPersonUserNameExistThenThrownEntityExistsException() {

		PersonDto personToSave = new PersonDto();
		personToSave.setUsername("carlos123");
		personToSave.setPhotoName("aaa");

		Person person = new Person();

		when(personDao.findByUsername("carlos123")).thenReturn(person);

		assertThatThrownBy(() -> {
			personService.save(personToSave);
		}).isExactlyInstanceOf(EntityExistsException.class).hasMessage("The person already exist in database");
	}

	@Test
	public void whenPersonUsernameDoesNotExistThenSaveThePersonAndReturnTheSavePerson() {
		PersonDto personDtoToSave = new PersonDto();
		personDtoToSave.setUsername("carlos_mario");
		personDtoToSave.setPhotoName("photo123.png");

		PersonDto exceptedSavedPersonDto = personDtoToSave;

		EntityFactory entityFactoryy = new EntityFactoryImpl();
		Person personReturnedByDao = (Person) entityFactoryy.create(personDtoToSave);
		Person personToSave = personReturnedByDao;

		EntityFactory entityFactory = mock(EntityFactory.class);
		when(personDao.save(personToSave)).thenReturn(personReturnedByDao);
		when(entityFactory.create(personDtoToSave)).thenReturn(personToSave);

		DtoFactory dtoFactory = new DtoFactoryImpl();
		personService = new PersonServiceImpl(personDao, dtoFactory, entityFactory);
		PersonDto actulSavedPersonDato = personService.save(personDtoToSave);

		assertThat(actulSavedPersonDato).isEqualToComparingFieldByField(exceptedSavedPersonDto);

	}

	// -------- TDD for existUsername(String username) -------//
	@Test
	public void whenUsernameIsNullThenThrownNullPointerException() {

		assertThatThrownBy(() -> {
			personService.existUsername(null);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The username can not be not null");
	}

	@Test
	public void whenUsernameExistInDbThenReturnTrue() {

		Person foundPerson = new Person();
		foundPerson.setUsername("carlos123");
		when(personDao.findByUsername("carlos123")).thenReturn(foundPerson);

		boolean existUser = personService.existUsername("carlos123");
		assertThat(existUser).isTrue();
	}

	@Test
	public void whenUsernameDoesNotExistInDbThenReturnFalse() {

		boolean existUser = personService.existUsername("carlos123");
		assertThat(existUser).isFalse();
	}
}
