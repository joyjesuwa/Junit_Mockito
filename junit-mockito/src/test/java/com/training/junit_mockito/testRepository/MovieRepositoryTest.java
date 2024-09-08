package com.training.junit_mockito.testRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.training.junit_mockito.model.Movie;
import com.training.junit_mockito.repository.IMovieRepository;

@DataJpaTest
public class MovieRepositoryTest {
	
	@Autowired
	private IMovieRepository movieRepository;
	
	@Test
	@DisplayName("It should save the movie to the database")
	void save() {
		Movie avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		
		Movie newMovie = movieRepository.save(avatarMovie);
		assertNotNull(newMovie);
		assertThat(newMovie.getId()).isNotEqualTo(null);
	}
	
	@Test
	@DisplayName("It should return the movies list with size of 2")
	void getAllMovies() {
		Movie avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		movieRepository.save(avatarMovie);
		
		Movie titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenres("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(2004, Month.JANUARY, 10));
		movieRepository.save(titanicMovie);
		
		List<Movie> list = movieRepository.findAll();
		
		assertNotNull(list);
		assertThat(list).isNotNull();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("It should return the movie by its id")
	void getMovieById() {
		Movie avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		movieRepository.save(avatarMovie);
		
		Movie newMovie = movieRepository.findById(avatarMovie.getId()).get();
		
		assertNotNull(newMovie);
		assertEquals("Action", newMovie.getGenres());
		assertThat(newMovie.getReleaseDate()).isBefore(LocalDate.of(2000, Month.APRIL, 24));
	}
	
	@Test
	@DisplayName("It should return the movies list with genera ROMANCE")
	void getMoviesByGenera() {
		Movie avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		movieRepository.save(avatarMovie);
		
		Movie titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenres("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(2004, Month.JANUARY, 10));
		movieRepository.save(titanicMovie);
		
		List<Movie> list = movieRepository.findByGenres("Romance");
		
		assertNotNull(list);
		assertThat(list.size()).isEqualTo(1);
	}
	
	@Test
	@DisplayName("It should update the movie genera with FANTACY")
	void updateMovie() {
		Movie avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		movieRepository.save(avatarMovie);
		
		Movie existingMovie = movieRepository.findById(avatarMovie.getId()).get();
		existingMovie.setGenres("Fantacy");
		Movie updatedMovie = movieRepository.save(existingMovie);
		
		assertEquals("Fantacy", updatedMovie.getGenres());
		assertEquals("Avatar", updatedMovie.getName());
	}
	
	@Test
	@DisplayName("It should delete the existing movie")
	void deleteMovie() {
		Movie avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 23));
		movieRepository.save(avatarMovie);
		Long id = avatarMovie.getId();
		
		Movie titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenres("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(2004, Month.JANUARY, 10));
		movieRepository.save(titanicMovie);
		
		movieRepository.delete(avatarMovie);
		
		List<Movie> list = movieRepository.findAll();
		
		Optional<Movie> exitingMovie = movieRepository.findById(id);
		
		assertEquals(1, list.size());
		assertThat(exitingMovie).isEmpty();
		
	}

}
