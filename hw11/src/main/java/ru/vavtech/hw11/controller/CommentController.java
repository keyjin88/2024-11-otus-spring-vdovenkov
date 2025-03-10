package ru.vavtech.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vavtech.hw11.model.Comment;
import ru.vavtech.hw11.repository.CommentRepository;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Comment> getCommentById(@PathVariable String id) {
        return commentRepository.findById(id);
    }

    @PostMapping
    public Mono<Comment> createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("/{id}")
    public Mono<Comment> updateComment(@PathVariable String id, @RequestBody Comment comment) {
        return commentRepository.findById(id)
                .flatMap(existingComment -> {
                    comment.setId(id);
                    return commentRepository.save(comment);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteComment(@PathVariable String id) {
        return commentRepository.findById(id)
                .flatMap(comment -> commentRepository.deleteById(id));
    }
} 