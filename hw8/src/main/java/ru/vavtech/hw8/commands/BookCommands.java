package ru.vavtech.hw8.commands;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.vavtech.hw8.converters.BookConverter;
import ru.vavtech.hw8.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(String id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %d not found".formatted(id));
    }

    @ShellMethod(key = "bins", value = "Insert book")
    public String createBook(String title, String authorId, List<String> genresIds) {
        return bookConverter.bookToString(bookService.create(title, authorId, genresIds));

    }

    @ShellMethod(key = "bupd", value = "Update book")
    public String updateBook(String id, String title, String authorId, List<String> genresIds) {
        var savedBook = bookService.update(id, title, authorId, genresIds);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(key = "bdel", value = "Delete book by id")
    public void deleteBook(String id) {
        bookService.deleteById(id);
    }
}