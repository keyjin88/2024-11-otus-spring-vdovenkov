package ru.vavtech.hw9.services;

import org.springframework.stereotype.Service;
import ru.vavtech.hw9.repositories.AuthorRepository;
import ru.vavtech.hw9.mapper.AuthorMapper;
import ru.vavtech.hw9.controller.response.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }
}