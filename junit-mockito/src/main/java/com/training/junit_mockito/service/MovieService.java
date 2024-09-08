package com.training.junit_mockito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.junit_mockito.model.Movie;
import com.training.junit_mockito.repository.IMovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
	
	@Autowired
    private IMovieRepository movieRepository;
	
	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	}
	
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
	
	public Movie getMovieById(Long id) {
		return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie found for the id "+id));	
	}
	
	public Movie updateMovie(Movie movie, Long id) {
		
		Movie existingMovie = movieRepository.findById(id).get();
		existingMovie.setGenres(movie.getGenres());
		existingMovie.setName(movie.getName());
		existingMovie.setReleaseDate(movie.getReleaseDate());
		return movieRepository.save(existingMovie);
	}
	
	public void deleteMovie(Long id) {
		Movie existingMovie = movieRepository.findById(id).get();
		movieRepository.delete(existingMovie);
		
	}

}
