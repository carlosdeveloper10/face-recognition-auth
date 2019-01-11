package com.cmsoft.fr.module.base.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.PersonEntity;
import com.cmsoft.fr.module.person.service.PersonDto;
import com.cmsoft.fr.module.recognition.service.RecognitionDto;

@Component
public class DtoFactoryImpl implements DtoFactory {

	@Override
	public EntityDto create(Entity entity) {
		ModelMapper mapper;

		if (entity == null)
			throw new NullPointerException("entity must can not be null");

		mapper = new ModelMapper();
		Class<?> dtoClassType = getRelacionatedDtoClass(entity);
		return (EntityDto) mapper.map(entity, dtoClassType);
	}

	private Class<?> getRelacionatedDtoClass(Entity entity) {
		Class<?> entityClassType = entity.getClass();
		if (entityClassType == PersonEntity.class) {
			return PersonDto.class;
		}

		throw new DtoNotFoundException("It was not posible to find the dto associted to entity.");
	}

	@Override
	public NoEntityDto create(String dtoName) {
		if (dtoName == null)
			throw new NullPointerException("dtoName is null.");

		if (dtoName.equals("RECOGNITION"))
			return new RecognitionDto();
		

		throw new DtoNotFoundException("It was not posible to find the dto associted to the dtoName.");
	}

}
