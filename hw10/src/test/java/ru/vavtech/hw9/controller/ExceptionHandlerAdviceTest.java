package ru.vavtech.hw9.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vavtech.hw10.controller.BookController;
import ru.vavtech.hw10.exceptions.NotFoundException;
import ru.vavtech.hw10.services.AuthorService;
import ru.vavtech.hw10.services.BookService;
import ru.vavtech.hw10.services.GenreService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Exception handler test")
@WebMvcTest(BookController.class)
class ExceptionHandlerAdviceTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private GenreService genreService;

    @DisplayName("Should return 404 page when entity not found")
    @Test
    void shouldReturn404PageWhenEntityNotFound() throws Exception {
        given(bookService.findById(1L))
                .willThrow(new NotFoundException("Book with id 1 not found"));

        mvc.perform(get("/edit/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @DisplayName("Should return 500 page on unexpected error")
    @Test
    void shouldReturn500PageOnUnexpectedError() throws Exception {
        given(bookService.findById(1L))
                .willThrow(new RuntimeException("Unexpected error"));

        mvc.perform(get("/edit/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error/500"));
    }
} 