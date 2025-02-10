package ru.vavtech.hw9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vavtech.hw9.controller.request.CreateBookRequest;
import ru.vavtech.hw9.models.dto.BookDto;
import ru.vavtech.hw9.services.AuthorService;
import ru.vavtech.hw9.services.BookService;
import ru.vavtech.hw9.services.GenreService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> authors = bookService.findAll().stream()
                .map(BookDto::fromDomainObject).toList();
        model.addAttribute("books", authors);
        return "books-list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new CreateBookRequest()); // Исправлено
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@Valid CreateBookRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "add-book";
        }
        bookService.create(request.getTitle(), request.getAuthorId(), request.getGenreId());
        return "redirect:/";
    }

}
