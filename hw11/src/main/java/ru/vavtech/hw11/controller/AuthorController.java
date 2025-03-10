package ru.vavtech.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<Author>> getAuthorById(@PathVariable String id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Author> createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Author>> updateAuthor(@PathVariable String id, @RequestBody Author author) {
        return authorRepository.findById(id)
                .flatMap(existingAuthor -> {
                    author.setId(id);
                    return authorRepository.save(author)
                            .map(ResponseEntity::ok);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable String id) {
        return authorRepository.findById(id)
                .flatMap(author -> authorRepository.deleteById(id)
                        .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
} 