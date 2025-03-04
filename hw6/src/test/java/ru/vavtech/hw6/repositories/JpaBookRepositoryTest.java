package ru.vavtech.hw6.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.vavtech.hw6.models.Author;
import ru.vavtech.hw6.models.Book;
import ru.vavtech.hw6.models.Comment;
import ru.vavtech.hw6.models.Genre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaBookRepository.class)
class JpaBookRepositoryTest {
    @Autowired
    private JpaBookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }
    
    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        var actualBook = bookRepository.findById(1);
        var expectedBook = em.find(Book.class, 1);
        assertThat(actualBook)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var expectedBooks = getDbBooks(dbAuthors, dbGenres);
        var actualBooks = bookRepository.findAll();

        assertThat(actualBooks)
                .hasSize(expectedBooks.size())
                .allSatisfy(actualBook -> {
                    assertThat(expectedBooks)
                            .anySatisfy(expectedBook -> {
                                assertThat(actualBook)
                                        .usingRecursiveComparison()
                                        .withComparatorForType(Comparator.comparingLong(Author::getId), Author.class)
                                        .withComparatorForType(Comparator.comparingLong(Genre::getId), Genre.class)
                                        .isEqualTo(expectedBook);
                            });
                });
    }
    
    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(0, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0), List.of());
        expectedBook = bookRepository.save(expectedBook);

        var actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(expectedBook)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(actualBook);
    }
    
    @DisplayName("должен сохранять изменённую книгу")
    @Test
    void shouldSaveUpdatedBook() {
        final var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2), List.of());
        var actualBook = em.find(Book.class, expectedBook.getId());

        Comparator<Author> authorComparator = Comparator.comparingLong(Author::getId);
        Comparator<Genre> genreComparator = Comparator.comparingLong(Genre::getId);

        assertThat(actualBook)
                .isNotNull()
                .usingRecursiveComparison()
                .withComparatorForType(authorComparator, Author.class)
                .withComparatorForType(genreComparator, Genre.class)
                .isNotEqualTo(expectedBook);

        bookRepository.save(expectedBook);
        actualBook = em.find(Book.class, expectedBook.getId());

        assertThat(actualBook)
                .isNotNull()
                .usingRecursiveComparison()
                .withComparatorForType(authorComparator, Author.class)
                .withComparatorForType(genreComparator, Genre.class)
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        var firstBook = em.find(Book.class, 1);
        assertThat(firstBook).isNotNull();

        bookRepository.deleteById(firstBook.getId());

        assertThat(em.find(Book.class, firstBook.getId())).isNull();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> {
                    var book = new Book(id, "Book_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1), new ArrayList<>());
                    if (id == 1) {
                        book.getComments().add(new Comment(1, "Comment #1 to book 1", book));
                        book.getComments().add(new Comment(2, "Comment #2 to book 1", book));
                    } else if (id == 2) {
                        book.getComments().add(new Comment(3, "Comment #1 to book 2", book));
                    }
                    return book;
                })
                .toList();
    }
}
