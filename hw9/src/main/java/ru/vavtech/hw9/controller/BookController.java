package ru.vavtech.hw9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vavtech.hw9.controller.request.BookCreateRequest;
import ru.vavtech.hw9.controller.request.BookUpdateRequest;
import ru.vavtech.hw9.models.dto.BookDto;
import ru.vavtech.hw9.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@Valid @RequestBody BookCreateRequest request) {
        return bookService.create(request.getTitle(), request.getAuthorId(), request.getGenreId());
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest request) {
        return bookService.update(id, request.getTitle(), request.getAuthorId(), request.getGenreId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
