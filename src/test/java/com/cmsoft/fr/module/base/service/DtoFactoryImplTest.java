package com.cmsoft.fr.module.base.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.PersonEntity;
import com.cmsoft.fr.module.person.service.PersonDto;

@RunWith(JUnit4.class)
public class DtoFactoryImplTest {

	public DtoFactory dtoFactory;

	// ------- TDD for create(Entity entity)
	@Test
	public void whenEntityIsnullThenThrownNullPointerExeption() {

		dtoFactory = new DtoFactoryImpl();
		assertThatThrownBy(() -> {
			dtoFactory.create(null);
		}).hasMessage("entity must can not be null");
	}

	@Test
	public void whenPassEntityThenReturnAsociatedDtoToEntity() {
		// Example: if student entity is passed, then it should return studentDto

		PersonEntity personEntityToPass = new PersonEntity();
		personEntityToPass.setId(1152440783);
		personEntityToPass.setPhotoName("carlos.png");
		personEntityToPass.setUsername("carlos");

		PersonDto expectedPersonDto = new PersonDto();
		expectedPersonDto.setId(personEntityToPass.getId());
		expectedPersonDto.setPhotoName(personEntityToPass.getPhotoName());
		expectedPersonDto.setUsername(personEntityToPass.getUsername());

		DtoFactory dtoFactory = new DtoFactoryImpl();
		Dto createdPersonDto = dtoFactory.create(personEntityToPass);

		assertThat(createdPersonDto).isEqualToComparingFieldByField(expectedPersonDto);
	}
	
	@Test
	public void whenEntityIsUknowForFactoryThenThrownEntityClassNotFoundException() {
		
		DtoFactory dtoFactory = new DtoFactoryImpl();
		
		Entity UknownEntity = new Entity() {};
		assertThatThrownBy(() -> {
			dtoFactory.create(UknownEntity);
		}).isExactlyInstanceOf(DtoNotFoundException.class).hasMessage("It was not posible to find the dto associted to entity.");
	}
	
}
