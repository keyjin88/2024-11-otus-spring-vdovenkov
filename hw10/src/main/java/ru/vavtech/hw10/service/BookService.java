package ru.vavtech.hw10.service;

import ru.vavtech.hw10.model.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(String title, long authorId, long genreId);

    BookDto update(long id, String title, long authorId, long genreId);

    void deleteById(long id);
}
