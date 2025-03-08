package ru.vavtech.hw9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vavtech.hw9.controller.request.BookCreateRequest;
import ru.vavtech.hw9.controller.request.BookUpdateRequest;
import ru.vavtech.hw9.exceptions.NotFoundException;
import ru.vavtech.hw9.models.dto.AuthorDto;
import ru.vavtech.hw9.models.dto.BookDto;
import ru.vavtech.hw9.models.dto.GenreDto;
import ru.vavtech.hw9.services.BookService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        List<BookDto> books = Arrays.asList(
            new BookDto(1L, "Book 1", new AuthorDto(1L, "Author 1"), new GenreDto(1L, "Genre 1")),
            new BookDto(2L, "Book 2", new AuthorDto(2L, "Author 2"), new GenreDto(2L, "Genre 2"))
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        BookDto book = new BookDto(1L, "Book 1", new AuthorDto(1L, "Author 1"), new GenreDto(1L, "Genre 1"));
        when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Book 1"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookCreateRequest request = new BookCreateRequest("New Book", 1L, 1L);
        BookDto book = new BookDto(1L, "New Book", new AuthorDto(1L, "Author 1"), new GenreDto(1L, "Genre 1"));
        when(bookService.create(request.getTitle(), request.getAuthorId(), request.getGenreId())).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BookUpdateRequest request = new BookUpdateRequest("Updated Book", 1L, 1L);
        BookDto book = new BookDto(1L, "Updated Book", new AuthorDto(1L, "Author 1"), new GenreDto(1L, "Genre 1"));
        when(bookService.update(1L, request.getTitle(), request.getAuthorId(), request.getGenreId())).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    // Тесты на валидацию
    @Test
    void shouldReturn400WhenCreateBookWithInvalidData() throws Exception {
        BookCreateRequest request = new BookCreateRequest("", null, null);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists())
                .andExpect(jsonPath("$.errors[0].message").exists());
    }

    @Test
    void shouldReturn400WhenUpdateBookWithInvalidData() throws Exception {
        BookUpdateRequest request = new BookUpdateRequest("", null, null);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists())
                .andExpect(jsonPath("$.errors[0].message").exists());
    }

    // Тесты на 404 ошибку
    @Test
    void shouldReturn404WhenBookNotFound() throws Exception {
        when(bookService.findById(999L)).thenThrow(new NotFoundException("Book not found"));

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentBook() throws Exception {
        BookUpdateRequest request = new BookUpdateRequest("Updated Book", 1L, 1L);
        when(bookService.update(999L, request.getTitle(), request.getAuthorId(), request.getGenreId()))
                .thenThrow(new NotFoundException("Book not found"));

        mockMvc.perform(put("/api/books/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    // Тесты на 500 ошибку
    @Test
    void shouldReturn500WhenServiceThrowsUnexpectedException() throws Exception {
        when(bookService.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Произошла внутренняя ошибка сервера"));
    }
} 