package ru.vavtech.hw10.services;

import ru.vavtech.hw10.models.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
