package com.cmsoft.fr.module.base.service;

import org.modelmapper.ModelMapper;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.Person;
import com.cmsoft.fr.module.person.service.PersonDto;

public class EntityFactoryImpl implements EntityFactory {

	@Override
	public Entity create(Dto dto) {
		
		if (dto == null)
			throw new NullPointerException("dto can not be null");

		ModelMapper mapper;
		mapper = new ModelMapper();
		Class<?> entityClassType = getRelacionatedDtoClass(dto);
		return (Entity) mapper.map(dto, entityClassType);
		
	}

	private Class<?> getRelacionatedDtoClass(Dto dto) {
		Class<?> dtoClassType = dto.getClass();
		if (dtoClassType == PersonDto.class) {
			return Person.class;
		}

		throw new EntityClassNotFoundException("It was not posible to find the dto associted to");
	}

}
