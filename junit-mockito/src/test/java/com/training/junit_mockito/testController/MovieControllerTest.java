package com.training.junit_mockito.testController;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.junit_mockito.model.Movie;
import com.training.junit_mockito.service.MovieService;

public class MovieControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MovieService movieService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Movie avatarMovie;
	private Movie titanicMovie;
	
	@BeforeEach
	void init() {
		avatarMovie = new Movie();
		avatarMovie.setId(1L);
		avatarMovie.setName("Avatar");
		avatarMovie.setGenres("Action");
		avatarMovie.setReleaseDate(LocalDate.of(1999, Month.APRIL, 22));
		
		titanicMovie = new Movie();
		avatarMovie.setId(2L);
		titanicMovie.setName("Titanic");
		titanicMovie.setGenres("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(2004, Month.JANUARY, 10));
	}
	
	@Test
	void shouldCreateNewMovie() throws Exception {
		
		when(movieService.save(any(Movie.class))).thenReturn(avatarMovie);
		
		this.mockMvc.perform(post("/movies")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(avatarMovie)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(avatarMovie.getName())))
		.andExpect(jsonPath("$.genera", is(avatarMovie.getGenres())))
		.andExpect(jsonPath("$.releaseDate", is(avatarMovie.getReleaseDate().toString())));
			
	}
	
	@Test
	void shouldFetchAllMovies() throws Exception {
		
		List<Movie> list = new ArrayList<>();
		list.add(avatarMovie);
		list.add(titanicMovie);
		
		when(movieService.getAllMovies()).thenReturn(list);
		
		this.mockMvc.perform(get("/movies"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(list.size())));
	}
	
	@Test
	void shouldFetchOneMovieById() throws Exception {
		
		when(movieService.getMovieById(anyLong())).thenReturn(avatarMovie);
		
		this.mockMvc.perform(get("/movies/{id}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(avatarMovie.getName())))
			.andExpect(jsonPath("$.genera", is(avatarMovie.getGenres())));
	}
	
	@Test
	void shouldDeleteMovie() throws Exception {
		
		doNothing().when(movieService).deleteMovie(anyLong());
		
		this.mockMvc.perform(delete("/movies/{id}", 1L))
			.andExpect(status().isNoContent());
			
	}
	
	@Test
	void shouldUpdateMovie() throws Exception {
		
		when(movieService.updateMovie(any(Movie.class), anyLong())).thenReturn(avatarMovie);		
		this.mockMvc.perform(put("/movies/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(avatarMovie)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(avatarMovie.getName())))
		.andExpect(jsonPath("$.genera", is(avatarMovie.getGenres())));
	}

}
