package ru.vavtech.hw9.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Author is required")
    private Long authorId;

    @NotNull(message = "Genre is required")
    private Long genreId;
}
