package ru.vavtech.hw8.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private String id;

    private String comment;

    private String bookId;
}
