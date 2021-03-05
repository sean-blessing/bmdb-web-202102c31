package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	@GetMapping("/")
	public List<Movie> getAll() {
		return movieRepo.findAll();
	}

	@GetMapping("/{id}")
	public Movie getById(@PathVariable int id) {
		return movieRepo.findById(id).get();
	}
	
	@PostMapping("/")
	public Movie create(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	@PutMapping("/")
	public Movie update(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	@DeleteMapping("/{id}")
	public Movie update(@PathVariable int id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			movieRepo.delete(movie.get());
		}
		else {
			System.out.println("Delete Error - movie not found for id: "+id);
		}
		return movie.get();
	}
	
	
	
	

}
