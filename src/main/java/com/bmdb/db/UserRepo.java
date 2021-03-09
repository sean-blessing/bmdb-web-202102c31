package com.bmdb.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUsernameAndPassword(String username, String password);

}
