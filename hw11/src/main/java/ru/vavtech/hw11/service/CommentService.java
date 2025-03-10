package ru.vavtech.hw11.service;


import ru.vavtech.hw11.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findByBookId(long bookId);

    CommentDto updateComment(long commentId, String commentText);

    CommentDto addComment(long bookId, String commentText);

    void deleteCommentById(long commentId);
}
