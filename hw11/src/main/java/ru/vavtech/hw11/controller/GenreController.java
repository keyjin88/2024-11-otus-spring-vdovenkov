package ru.vavtech.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vavtech.hw11.model.Genre;
import ru.vavtech.hw11.repository.GenreRepository;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Genre> getGenreById(@PathVariable String id) {
        return genreRepository.findById(id);
    }

    @PostMapping
    public Mono<Genre> createGenre(@RequestBody Genre genre) {
        return genreRepository.save(genre);
    }

    @PutMapping("/{id}")
    public Mono<Genre> updateGenre(@PathVariable String id, @RequestBody Genre genre) {
        return genreRepository.findById(id)
                .flatMap(existingGenre -> {
                    genre.setId(id);
                    return genreRepository.save(genre);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteGenre(@PathVariable String id) {
        return genreRepository.findById(id)
                .flatMap(genre -> genreRepository.deleteById(id));
    }
} 