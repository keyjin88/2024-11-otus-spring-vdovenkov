package ru.vavtech.hw10.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vavtech.hw10.models.dto.BookDto;
import ru.vavtech.hw10.models.dto.UpdateBookDto;
import ru.vavtech.hw10.services.BookService;

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
    public ResponseEntity<BookDto> getBook(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody UpdateBookDto bookDto) {
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
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 