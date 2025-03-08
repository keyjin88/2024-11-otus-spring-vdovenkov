package ru.vavtech.hw9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vavtech.hw9.models.dto.BookEditDto;
import ru.vavtech.hw9.services.AuthorService;
import ru.vavtech.hw9.services.BookService;
import ru.vavtech.hw9.services.GenreService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String listBooksPage(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books-list";
    }

    @GetMapping("/add")
    public String addBookPage(Model model) {
        model.addAttribute("book", new BookEditDto());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") BookEditDto book) {
        bookService.create(book.getTitle(), book.getAuthorId(), book.getGenreId());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editBookPage(@PathVariable("id") long id, Model model) {
        var bookDto = bookService.findById(id);
        model.addAttribute("book", BookEditDto.fromBookDto(bookDto));
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "edit-book";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id,
                           @ModelAttribute("book") BookEditDto book) {
        bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreId());
        return "redirect:/";
    }
} 