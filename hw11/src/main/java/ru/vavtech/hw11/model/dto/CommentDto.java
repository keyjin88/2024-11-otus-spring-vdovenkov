package ru.vavtech.hw11.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

    private long id;

    private String comment;

    private long bookId;
}
