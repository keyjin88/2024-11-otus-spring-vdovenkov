package ru.vavtech.hw9.converters;

import org.springframework.stereotype.Component;
import ru.vavtech.hw9.models.Author;
import ru.vavtech.hw9.models.dto.AuthorDto;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
} 