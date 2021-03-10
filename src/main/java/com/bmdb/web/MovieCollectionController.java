package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.MovieCollection;
import com.bmdb.business.User;
import com.bmdb.db.MovieCollectionRepo;
import com.bmdb.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movie-collections")
public class MovieCollectionController {
	
	@Autowired
	private MovieCollectionRepo movieCollectionRepo;
	@Autowired
	private UserRepo userRepo;
	
	// list all movieCollections
	@GetMapping("/")
	public List<MovieCollection> getAllMovieCollections() {
		return movieCollectionRepo.findAll();
	}
	
	// get movieCollection by id
	@GetMapping("/{id}")
	public Optional<MovieCollection> getMovieCollection(@PathVariable int id) {
		Optional<MovieCollection> a = movieCollectionRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MovieCollection not found");
		}
	}
	
	// add a movieCollection
	@PostMapping("/")
	public MovieCollection addMovieCollection(@RequestBody MovieCollection mc) {
		User u = mc.getUser();
		//save mc
		movieCollectionRepo.save(mc);
		// get all mc's for this user
		List<MovieCollection> mcs = movieCollectionRepo.
				findAllByUserId(u.getId());
		// declare newTotal = 0
		double newTotal = 0.0;
		// loop thru mcs
		for (MovieCollection movieCollection: mcs) {
			// add purchasePrice to newTotal
			newTotal += movieCollection.getPurchasePrice();
		}
		// set newTotal in user
		u.setCollectionValue(newTotal);
		
		// save user
		userRepo.save(u);
		
		return mc;
	}
	
	// update a movieCollection
	@PutMapping("/")
	public MovieCollection updateMovieCollection(@RequestBody MovieCollection a) {
		return movieCollectionRepo.save(a);
	}
	
	// delete a movieCollection
	@DeleteMapping("/{id}")
	public MovieCollection deleteMovieCollection(@PathVariable int id) {
		Optional<MovieCollection> mc = movieCollectionRepo.findById(id);
		if (mc.isPresent()) {
			movieCollectionRepo.deleteById(id);
		}
		return mc.get();
	}
}
