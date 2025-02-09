package ru.vavtech.hw8.services;


import ru.vavtech.hw8.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(String id);

    List<Book> findAll();

    Book create(String title, String authorId, List<String> genreIds);

    Book update(String id, String title, String authorId, List<String> genreIds);

    void deleteById(String id);
}
