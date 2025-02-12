package ru.vavtech.hw9.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vavtech.hw9.models.Author;
import ru.vavtech.hw9.models.Book;
import ru.vavtech.hw9.models.Genre;
import ru.vavtech.hw9.services.AuthorService;
import ru.vavtech.hw9.services.BookService;
import ru.vavtech.hw9.services.GenreService;

import java.util.Collections;
import java.util.Optional;

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
        Mockito.when(bookService.findAll()).thenReturn(Collections.emptyList());

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
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setAuthor(new Author(100L, "100 Author"));
        book.setGenre(new Genre(100L, "100 Genre"));
        Mockito.when(bookService.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(authorService.findAll()).thenReturn(Collections.singletonList(new Author(1L, "Author")));
        Mockito.when(genreService.findAll()).thenReturn(Collections.singletonList(new Genre(1L, "Genre")));

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-book"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        mockMvc.perform(post("/update/1")
                        .param("title", "Updated Book")
                        .param("author.id", "1")
                        .param("genre.id", "1"))
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