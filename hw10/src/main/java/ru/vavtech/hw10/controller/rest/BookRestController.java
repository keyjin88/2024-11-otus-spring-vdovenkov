package ru.vavtech.hw10.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.vavtech.hw10.model.dto.BookDto;
import ru.vavtech.hw10.model.dto.CreateBookDto;
import ru.vavtech.hw10.model.dto.UpdateBookDto;
import ru.vavtech.hw10.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody CreateBookDto bookDto) {
        return ResponseEntity.ok(
            bookService.create(bookDto.getTitle(), bookDto.getAuthorId(), bookDto.getGenreId())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateBookDto bookDto) {
        return ResponseEntity.ok(
            bookService.update(id, bookDto.getTitle(), bookDto.getAuthorId(), bookDto.getGenreId())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 