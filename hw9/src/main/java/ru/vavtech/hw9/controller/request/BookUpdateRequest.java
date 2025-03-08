package ru.vavtech.hw9.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    @NotBlank(message = "Название книги не может быть пустым")
    private String title;

    @NotNull(message = "ID автора не может быть пустым")
    private Long authorId;

    @NotNull(message = "ID жанра не может быть пустым")
    private Long genreId;
} 