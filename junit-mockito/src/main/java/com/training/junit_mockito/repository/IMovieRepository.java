package com.training.junit_mockito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.junit_mockito.model.Movie;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {
	
	List<Movie> findByGenres(String s);

}
