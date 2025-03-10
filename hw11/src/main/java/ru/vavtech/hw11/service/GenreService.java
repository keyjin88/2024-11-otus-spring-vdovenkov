package ru.vavtech.hw11.service;

import ru.vavtech.hw11.model.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
