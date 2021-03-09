package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.User;
import com.bmdb.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	// list all users
	@GetMapping("/")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	// authenticate via GET
	@GetMapping("")
	public User login(@RequestParam String username, @RequestParam String password) {
		return userRepo.findByUsernameAndPassword(username, password);
	}
	
	
	
	
	
	// get user by id
	@GetMapping("/{id}")
	public Optional<User> getUser(@PathVariable int id) {
		Optional<User> a = userRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
	}
	
	// add a user
	@PostMapping("/")
	public User addUser(@RequestBody User a) {
		return userRepo.save(a);
	}
	
	// update a user
	@PutMapping("/")
	public User updateUser(@RequestBody User a) {
		return userRepo.save(a);
	}
	
	// delete a user
	@DeleteMapping("/{id}")
	public Optional<User> deleteUser(@PathVariable int id) {
		Optional<User> a = userRepo.findById(id);
		if (a.isPresent()) {
			try {
				userRepo.deleteById(id);
			}
			catch (DataIntegrityViolationException dive) {
				// catch dive when user exists as fk on another table
				System.err.println(dive.getRootCause().getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foreign Key Constraint Issue - User id " + id + " is refered to elsewhere.");
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception caught during User delete.");				
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return a;
	}
}
