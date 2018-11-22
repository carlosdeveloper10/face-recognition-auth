package com.cmsoft.fr.module.base.service;

import org.modelmapper.ModelMapper;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.Person;
import com.cmsoft.fr.module.person.service.PersonDto;

public class DtoFactoryImpl implements DtoFactory {

	private ModelMapper mapper;

	@Override
	public Dto create(Entity entity) {
		if (entity == null)
			throw new NullPointerException("entity must can not be null");

		mapper = new ModelMapper();
		Class<?> dtoClassType = getRelacionatedDtoClass(entity);
		return (Dto) mapper.map(entity, dtoClassType);

	}

	private Class<?> getRelacionatedDtoClass(Entity entity) {
		Class<?> entityClassType = entity.getClass();
		if (entityClassType == Person.class) {
			return PersonDto.class;
		}

		throw new DtoNotFoundException("It was not posible to find the dto associted to");
	}

}
