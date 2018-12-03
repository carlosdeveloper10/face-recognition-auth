package com.cmsoft.fr.module.base.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.PersonEntity;
import com.cmsoft.fr.module.person.service.PersonDto;

@Component
public class DtoFactoryImpl implements DtoFactory {

	@Override
	public Dto create(Entity entity) {
		ModelMapper mapper;
		
		if (entity == null)
			throw new NullPointerException("entity must can not be null");

		mapper = new ModelMapper();
		Class<?> dtoClassType = getRelacionatedDtoClass(entity);
		return (Dto) mapper.map(entity, dtoClassType);
	}

	private Class<?> getRelacionatedDtoClass(Entity entity) {
		Class<?> entityClassType = entity.getClass();
		if (entityClassType == PersonEntity.class) {
			return PersonDto.class;
		}

		throw new DtoNotFoundException("It was not posible to find the dto associted to entity.");
	}

}