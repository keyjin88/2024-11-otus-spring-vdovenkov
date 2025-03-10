package ru.vavtech.hw11.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vavtech.hw11.model.Book;
import ru.vavtech.hw11.model.dto.BookDto;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                authorMapper.toDto(book.getAuthor()),
                genreMapper.toDto(book.getGenre()));
    }
} 