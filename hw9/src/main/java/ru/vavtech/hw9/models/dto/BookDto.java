package ru.vavtech.hw9.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vavtech.hw9.models.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorDto.fromDomainObject(book.getAuthor()),
                GenreDto.fromDomainObject(book.getGenre())
        );
    }
}
