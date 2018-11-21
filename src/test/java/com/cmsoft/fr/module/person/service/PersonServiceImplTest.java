package com.cmsoft.fr.module.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmsoft.fr.module.person.data.dao.PersonDao;
import com.cmsoft.fr.module.person.data.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

	@Mock
	private PersonDao personDao;

	@InjectMocks
	public PersonServiceImpl personService;

	// -------- TDD for save(Person person) -------//
	@Test
	public void whenPersonIsNullThenThrownNullPointerException() {

		assertThatThrownBy(() -> {
			personService.save(null);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The entity person must be not null");
	}

	@Test
	public void whenAMandatoryPersonAttributeIsIllegalThenThrownIllegalArgumentException() {

		Person personToSave = new Person();

		assertThatThrownBy(() -> {
			personService.save(personToSave);
		}).isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Check person attributes, one or more mandatory attributes are illegal");
	}

	@Test
	public void whenPersonUserNameExistThenThrownEntityExistsException() {

		Person personToSave = new Person();
		personToSave.setUsername("carlos123");
		personToSave.setPhotoName("aaa");
		when(personDao.findByUsername("carlos123")).thenReturn(personToSave);

		assertThatThrownBy(() -> {
			personService.save(personToSave);
		}).isExactlyInstanceOf(EntityExistsException.class).hasMessage("The person already exist in database");
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
