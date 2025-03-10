package ru.vavtech.hw11.mapper;

import org.springframework.stereotype.Component;
import ru.vavtech.hw11.model.Genre;
import ru.vavtech.hw11.model.dto.GenreDto;

@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
} 