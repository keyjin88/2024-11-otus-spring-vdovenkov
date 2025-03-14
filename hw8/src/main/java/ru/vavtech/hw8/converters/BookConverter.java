package ru.vavtech.hw8.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vavtech.hw8.models.Book;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(Book book) {
        String genresString = "";
        if (book.getGenres() != null) {
            genresString = book.getGenres().stream()
                    .map(genreConverter::genreToString)
                    .map("{%s}"::formatted)
                    .collect(Collectors.joining(", "));
        }
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genresString);
    }
}