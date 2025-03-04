package ru.vavtech.hw9.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDto {

    @NotNull(message = "ID is required")
    @Positive(message = "ID must be positive")
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Author is required")
    @Positive(message = "Author ID must be positive")
    private Long authorId;

    @NotNull(message = "Genre is required")
    @Positive(message = "Genre ID must be positive")
    private Long genreId;
} 