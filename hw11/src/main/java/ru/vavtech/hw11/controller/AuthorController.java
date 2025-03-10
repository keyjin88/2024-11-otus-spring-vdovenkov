package ru.vavtech.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vavtech.hw11.model.Author;
import ru.vavtech.hw11.repository.AuthorRepository;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthorById(@PathVariable String id) {
        return authorRepository.findById(id);
    }

    @PostMapping
    public Mono<Author> createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public Mono<Author> updateAuthor(@PathVariable String id, @RequestBody Author author) {
        return authorRepository.findById(id)
                .flatMap(existingAuthor -> {
                    author.setId(id);
                    return authorRepository.save(author);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable String id) {
        return authorRepository.findById(id)
                .flatMap(author -> authorRepository.deleteById(id));
    }
} 