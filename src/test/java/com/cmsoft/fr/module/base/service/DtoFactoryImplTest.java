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
import com.cmsoft.fr.module.recognition.service.RecognitionDto;

@RunWith(JUnit4.class)
public class DtoFactoryImplTest {

	public DtoFactory dtoFactory;

	// ------- TDD for create(Entity entity)
	@Test
	public void whenEntityIsnullThenThrownNullPointerExeption() {

		dtoFactory = new DtoFactoryImpl();
		PersonEntity p = null;
		assertThatThrownBy(() -> {
			dtoFactory.create(p);
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
		EntityDto createdPersonDto = dtoFactory.create(personEntityToPass);

		assertThat(createdPersonDto).isEqualToComparingFieldByField(expectedPersonDto);
	}

	@Test
	public void whenEntityIsUknowForFactoryThenThrownEntityClassNotFoundException() {

		DtoFactory dtoFactory = new DtoFactoryImpl();

		Entity UknownEntity = new Entity() {
		};
		assertThatThrownBy(() -> {
			dtoFactory.create(UknownEntity);
		}).isExactlyInstanceOf(DtoNotFoundException.class)
				.hasMessage("It was not posible to find the dto associted to entity.");
	}

	// ------- TDD for create(String dtoDame)
	@Test
	public void whenDtoNameIsnullThenThrownNullPointerExeption() {

		dtoFactory = new DtoFactoryImpl();
		String dtoName = null;
		assertThatThrownBy(() -> {
			dtoFactory.create(dtoName);
		}).isExactlyInstanceOf(NullPointerException.class).hasMessage("dtoName is null.");
	}

	@Test
	public void whenPassCorrectDtoNameThenReturnAsociatedDto() {
		DtoFactory dtoFactory = new DtoFactoryImpl();
		NoEntityDto actualDto = dtoFactory.create("RECOGNITION");

		assertThat(actualDto).isNotNull().isExactlyInstanceOf(RecognitionDto.class);
	}
	
	@Test
	public void whenDtoNameIsUknowForFactoryThenThrownDtoNotFoundException() {

		DtoFactory dtoFactory = new DtoFactoryImpl();

		assertThatThrownBy(() -> {
			dtoFactory.create("NO_EXISTE");
		}).isExactlyInstanceOf(DtoNotFoundException.class)
				.hasMessage("It was not posible to find the dto associted to the dtoName.");
	}
}
