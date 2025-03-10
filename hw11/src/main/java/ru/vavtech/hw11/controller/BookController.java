package ru.vavtech.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vavtech.hw11.model.Book;
import ru.vavtech.hw11.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Book>> getBookById(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Book>> createBook(@RequestBody Book book) {
        return bookRepository.save(book)
                .map(savedBook -> ResponseEntity.status(201).body(savedBook));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Book>> updateBook(@PathVariable String id, @RequestBody Book book) {
        return bookRepository.findById(id)
                .flatMap(existingBook -> {
                    book.setId(id);
                    return bookRepository.save(book)
                            .map(ResponseEntity::ok);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable String id) {
        return bookRepository.findById(id)
                .flatMap(book -> bookRepository.deleteById(id)
                        .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
} 