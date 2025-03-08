package ru.vavtech.hw10.services;

import ru.vavtech.hw10.models.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
