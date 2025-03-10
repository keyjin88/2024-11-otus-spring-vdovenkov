package ru.vavtech.hw9.services;

import ru.vavtech.hw9.models.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
