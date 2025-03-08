package ru.vavtech.hw10.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vavtech.hw10.mapper.AuthorMapper;
import ru.vavtech.hw10.models.dto.AuthorDto;
import ru.vavtech.hw10.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }
}
