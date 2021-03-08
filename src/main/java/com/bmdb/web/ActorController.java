package com.bmdb.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/actors")
public class ActorController {
	
	@Autowired
	private ActorRepo actorRepo;
	
	// list all actors
	@GetMapping("")
	public List<Actor> getAllActors() {
		return actorRepo.findAll();
	}
	
	// get actor by id
	@GetMapping("/{id}")
	public Optional<Actor> getActor(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found");
		}
	}
	
	// add a actor
	@PostMapping("/")
	public Actor addActor(@RequestBody Actor a) {
		return actorRepo.save(a);
	}
	
	// update a actor
	@PutMapping("/")
	public Actor updateActor(@RequestBody Actor a) {
		return actorRepo.save(a);
	}
	
	// delete a actor
	@DeleteMapping("/{id}")
	public Optional<Actor> deleteActor(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			try {
				actorRepo.deleteById(id);
			}
			catch (DataIntegrityViolationException dive) {
				// catch dive when actor exists as fk on another table
				System.err.println(dive.getRootCause().getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foreign Key Constraint Issue - Actor id " + id + " is refered to elsewhere.");
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception caught during Actor delete.");				
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found.");
		}
		return a;
	}
	
	// list all actors by gender
	// using RequestParam to pass gender
	@GetMapping("/find-by-gender")
	public List<Actor> getActorsByGender(@RequestParam String gender) {
		return actorRepo.findByGender(gender);
	}
	
	// list all actors whose last name starts with 'letter'
	@GetMapping("/find-lastname-starts-with")
	public List<Actor> getActorsLastNameStarts(@RequestParam String letter) {
		return actorRepo.findByLastNameLike(letter+"%");
	}
	
	// list all actors born between d1 and d2
	// Note:  this uses some additional annotations on the incoming request parameters.
	// This is necessary because, by default, Spring cannot convert string parameters
	// from your request into LocalDate objects.
	@GetMapping("/find-by-birthdate-between")
	public List<Actor> getActorsByBirthDateBetween(
			@RequestParam("ld1") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld1, 
			@RequestParam("ld2") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ld2) {
		//LocalDate ld1 = LocalDate.parse(d1);
		//LocalDate ld2 = LocalDate.parse(d2);
		
		return actorRepo.findByBirthDateBetween(ld1, ld2);
	}
	
	
	
	
	
	
	
	
	
	
	
}
