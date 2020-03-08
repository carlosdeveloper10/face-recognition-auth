package com.cmsoft.fr.module.person.data.dao;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cmsoft.fr.module.person.data.entity.PersonEntity;

@EnableScan
@Repository
public interface PersonDao extends CrudRepository<PersonEntity, Long> {

	/**
	 * Find the person by its username.
	 * 
	 * @return
	 */
	PersonEntity findByUsername(String username);
}
