package ru.vavtech.hw9.services;

import org.springframework.stereotype.Service;
import ru.vavtech.hw9.repositories.GenreRepository;
import ru.vavtech.hw9.mapper.GenreMapper;
import ru.vavtech.hw9.controller.response.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }
}