package com.cmsoft.fr.module.base.service;

import com.cmsoft.fr.module.base.data.entity.Entity;

public interface DtoFactory {

	EntityDto create(Entity entity);
	
	NoEntityDto create(String dto);
}
