package ru.vavtech.hw10.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vavtech.hw10.models.dto.GenreDto;
import ru.vavtech.hw10.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreRestController.class)
@DisplayName("Genre REST controller should")
class GenreRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("correctly return list of all genres")
    void shouldReturnCorrectGenresList() throws Exception {
        var genres = List.of(
            new GenreDto(1L, "Genre 1"),
            new GenreDto(2L, "Genre 2")
        );
        given(genreService.findAll()).willReturn(genres);

        mvc.perform(get("/api/genres"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(genres)));
    }
} 