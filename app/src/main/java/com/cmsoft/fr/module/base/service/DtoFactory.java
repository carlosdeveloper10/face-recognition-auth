package com.cmsoft.fr.module.base.service;

import com.cmsoft.fr.module.base.data.entity.Entity;
import com.cmsoft.fr.module.base.data.entity.Entity;

/**
 * Creates all DTOs in the app.
 * 
 * @author carlos
 *
 */
public interface DtoFactory {

	EntityDto create(Entity entity);

	NoEntityDto create(String dtoName);
}
