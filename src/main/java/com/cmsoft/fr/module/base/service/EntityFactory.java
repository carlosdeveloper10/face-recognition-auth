package com.cmsoft.fr.module.base.service;

import com.cmsoft.fr.module.base.data.entity.Entity;

/**
 * Factory used to create all entities.
 * 
 * @author carlos
 *
 */
public interface EntityFactory {

	Entity create(EntityDto dto);
}
