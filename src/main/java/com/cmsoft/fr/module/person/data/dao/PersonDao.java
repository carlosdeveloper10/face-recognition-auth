package com.cmsoft.fr.module.person.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmsoft.fr.module.person.data.entity.Person;

public interface PersonDao extends JpaRepository<Person, Long> {

	
}
