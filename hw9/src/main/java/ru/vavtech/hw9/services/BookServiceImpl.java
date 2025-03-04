package ru.vavtech.hw9.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vavtech.hw9.converters.BookMapper;
import ru.vavtech.hw9.exceptions.EntityNotFoundException;
import ru.vavtech.hw9.models.Author;
import ru.vavtech.hw9.models.Book;
import ru.vavtech.hw9.models.Genre;
import ru.vavtech.hw9.models.dto.BookDto;
import ru.vavtech.hw9.repositories.AuthorRepository;
import ru.vavtech.hw9.repositories.BookRepository;
import ru.vavtech.hw9.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto create(String title, long authorId, long genreId) {
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(authorById(authorId));
        book.setGenre(genreById(genreId));
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, long genreId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        book.setTitle(title);
        book.setAuthor(authorById(authorId));
        book.setGenre(genreById(genreId));
        return bookMapper.toDto(bookRepository.save(book));
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