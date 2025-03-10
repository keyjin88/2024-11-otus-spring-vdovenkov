package ru.vavtech.hw11.mapper;

import org.springframework.stereotype.Component;
import ru.vavtech.hw11.model.Comment;
import ru.vavtech.hw11.model.dto.CommentDto;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getBook().getId());
    }
} 