package com.bmdb.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.Actor;

public interface ActorRepo extends JpaRepository<Actor, Integer> {
	//find actors by gender
	List<Actor> findByGender(String gender);
	
	// find actors whose last name starts with...
	List<Actor> findByLastNameLike(String letter);
	
	//find actors born in the 60s
	List<Actor> findByBirthDateBetween(LocalDate d1, LocalDate d2);

}
