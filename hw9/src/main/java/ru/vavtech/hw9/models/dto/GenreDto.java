package ru.vavtech.hw9.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vavtech.hw9.models.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private Long id;
    private String name;

    public static GenreDto fromDomainObject(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

}
