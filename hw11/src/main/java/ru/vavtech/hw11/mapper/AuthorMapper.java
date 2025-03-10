package ru.vavtech.hw11.mapper;

import org.springframework.stereotype.Component;
import ru.vavtech.hw11.model.Author;
import ru.vavtech.hw11.model.dto.AuthorDto;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
} 