package com.cmsoft.fr.module.base.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.person.data.entity.PersonEntity;
import com.cmsoft.fr.module.person.service.PersonDto;

@Component
public class EntityFactoryImpl implements EntityFactory {

	@Override
	public Entity create(EntityDto dto) {
		
		if (dto == null)
			throw new NullPointerException("dto can not be null");

		ModelMapper mapper;
		mapper = new ModelMapper();
		Class<?> entityClassType = getRelacionatedDtoClass(dto);
		return (Entity) mapper.map(dto, entityClassType);
		
	}

	private Class<?> getRelacionatedDtoClass(EntityDto dto) {
		Class<?> dtoClassType = dto.getClass();
		if (dtoClassType == PersonDto.class) {
			return PersonEntity.class;
		}

		throw new EntityClassNotFoundException("It was not posible to find the dto associted to entity.");
	}

}
