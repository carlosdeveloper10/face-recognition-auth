package com.cmsoft.fr.module.base.service;

import com.cmsoft.fr.module.base.data.entity.Entity;

public interface EntityFactory {

	Entity create(EntityDto dto); 
}
