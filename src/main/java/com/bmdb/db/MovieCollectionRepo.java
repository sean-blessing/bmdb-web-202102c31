package com.bmdb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.MovieCollection;

public interface MovieCollectionRepo extends JpaRepository<MovieCollection, Integer> {
	List<MovieCollection> findAllByUserId(int id);
}
