package ru.vavtech.hw6.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vavtech.hw6.exceptions.EntityNotFoundException;
import ru.vavtech.hw6.models.Author;
import ru.vavtech.hw6.models.Book;
import ru.vavtech.hw6.models.Genre;
import ru.vavtech.hw6.repositories.AuthorRepository;
import ru.vavtech.hw6.repositories.BookRepository;
import ru.vavtech.hw6.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book create(String title, long authorId, long genreId) {
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(authorById(authorId));
        book.setGenre(genreById(genreId));
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(long id, String title, long authorId, long genreId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        book.setTitle(title);
        book.setAuthor(authorById(authorId));
        book.setGenre(genreById(genreId));
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Author authorById(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
    }

    private Genre genreById(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
    }
}