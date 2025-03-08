package ru.vavtech.hw9.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEditDto {
    private Long id;
    private String title;
    private Long authorId;
    private Long genreId;

    public static BookEditDto fromBookDto(BookDto bookDto) {
        return new BookEditDto(
            bookDto.getId(),
            bookDto.getTitle(),
            bookDto.getAuthor().getId(),
            bookDto.getGenre().getId()
        );
    }
} 