package com.cmsoft.fr.module.person.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

	@InjectMocks
	public PersonServiceImpl personService;

	@Test
	public void whenPersonIsNullThenThrownNullPointerException() {

		assertThatThrownBy(() -> {
			personService.save(null);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("The entity person must be not null");
	}

}
