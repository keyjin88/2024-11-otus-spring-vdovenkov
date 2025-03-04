package ru.vavtech.hw9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vavtech.hw9.controller.request.CreateBookDto;
import ru.vavtech.hw9.controller.request.UpdateBookDto;
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
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new CreateBookDto());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@Valid CreateBookDto request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", request);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "add-book";
        }
        bookService.create(request.getTitle(), request.getAuthorId(), request.getGenreId());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        var book = bookService.findById(id);
        var updateBookDto = new UpdateBookDto(
            book.getId(),
            book.getTitle(),
            book.getAuthor().getId(),
            book.getGenre().getId()
        );
        model.addAttribute("book", updateBookDto);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "edit-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @Valid UpdateBookDto bookDto,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", bookDto);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "edit-book";
        }
        bookService.update(id, bookDto.getTitle(), bookDto.getAuthorId(), bookDto.getGenreId());
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
