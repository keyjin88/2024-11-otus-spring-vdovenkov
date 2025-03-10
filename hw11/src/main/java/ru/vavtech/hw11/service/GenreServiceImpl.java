package ru.vavtech.hw11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vavtech.hw11.mapper.GenreMapper;
import ru.vavtech.hw11.model.dto.GenreDto;
import ru.vavtech.hw11.repository.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .toList();
    }
}
