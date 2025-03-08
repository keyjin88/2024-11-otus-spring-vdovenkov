package ru.vavtech.hw9.services;

import ru.vavtech.hw9.models.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
