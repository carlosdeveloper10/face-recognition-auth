package com.cmsoft.fr.module.base.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.Person;
import com.cmsoft.fr.module.person.service.PersonDto;

@RunWith(JUnit4.class)
public class EntityFactoryImplTest {

	@Test
	public void whenSomeDtoIsPassedThenReturnTheAsociatedEntityToDto() {
		// Example: if student entity is passed, then it should return studentDto

		PersonDto personDtoToPass = new PersonDto();
		personDtoToPass.setId(1152440783);
		personDtoToPass.setPhotoName("carlos.png");
		personDtoToPass.setUsername("carlos");

		Person expectedPersonEntity = new Person();
		expectedPersonEntity.setId(personDtoToPass.getId());
		expectedPersonEntity.setPhotoName(personDtoToPass.getPhotoName());
		expectedPersonEntity.setUsername(personDtoToPass.getUsername());

		EntityFactory entityFactory = new EntityFactoryImpl();
		Entity createdPersonEntity = entityFactory.create(personDtoToPass);

		assertThat(expectedPersonEntity).isEqualToComparingFieldByField(createdPersonEntity);
	}

}
