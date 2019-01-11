package com.cmsoft.fr.module.person.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmsoft.fr.module.person.data.entity.PersonEntity;

@Repository("com.cmsoft.fr.module.person.data.dao.PersonDao")
public interface PersonDao extends JpaRepository<PersonEntity, Long> {

	/**
	 * Find the person by its username.
	 * 
	 * @return
	 */
	PersonEntity findByUsername(String username);
}
