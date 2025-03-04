package ru.vavtech.hw9.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vavtech.hw9.models.dto.AuthorDto;
import ru.vavtech.hw9.models.dto.BookDto;
import ru.vavtech.hw9.models.dto.GenreDto;
import ru.vavtech.hw9.services.AuthorService;
import ru.vavtech.hw9.services.BookService;
import ru.vavtech.hw9.services.GenreService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private GenreService genreService;

    @Test
    public void testListPage() throws Exception {
        var bookDto = new BookDto(1L, "Sample Book",
                new AuthorDto(1L, "Author"),
                new GenreDto(1L, "Genre"));

        Mockito.when(bookService.findAll()).thenReturn(Collections.singletonList(bookDto));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("books-list"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    public void testShowAddBookForm() throws Exception {
        Mockito.when(authorService.findAll()).thenReturn(Collections.emptyList());
        Mockito.when(genreService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"));
    }

    @Test
    public void testAddBook() throws Exception {
        mockMvc.perform(post("/add")
                        .param("title", "New Book")
                        .param("authorId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testShowEditBookForm() throws Exception {
        var bookDto = new BookDto(1L, "Sample Book",
                new AuthorDto(100L, "100 Author"),
                new GenreDto(100L, "100 Genre"));
        
        Mockito.when(bookService.findById(1L)).thenReturn(bookDto);
        Mockito.when(authorService.findAll()).thenReturn(Collections.singletonList(new AuthorDto(1L, "Author")));
        Mockito.when(genreService.findAll()).thenReturn(Collections.singletonList(new GenreDto(1L, "Genre")));

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-book"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        var bookDto = new BookDto(1L, "Updated Book",
                new AuthorDto(1L, "Author"),
                new GenreDto(1L, "Genre"));
        
        Mockito.when(bookService.update(eq(1L), any(), anyLong(), anyLong())).thenReturn(bookDto);

        mockMvc.perform(post("/update/1")
                        .param("id", "1")
                        .param("title", "Updated Book")
                        .param("authorId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(post("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}