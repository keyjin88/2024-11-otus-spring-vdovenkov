package ru.vavtech.hw11.service;

import ru.vavtech.hw11.model.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
