package ru.vavtech.hw11.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vavtech.hw11.model.Author;
import ru.vavtech.hw11.model.Genre;
import ru.vavtech.hw11.model.Book;
import ru.vavtech.hw11.repository.AuthorRepository;
import ru.vavtech.hw11.repository.GenreRepository;
import ru.vavtech.hw11.repository.BookRepository;
import reactor.core.publisher.Mono;

@Configuration
public class MongoInitializer {

    @Bean
    public CommandLineRunner initDatabase(AuthorRepository authorRepository,
                                        GenreRepository genreRepository,
                                        BookRepository bookRepository) {
        return args -> {
            // Очищаем все коллекции
            authorRepository.deleteAll()
                .then(genreRepository.deleteAll())
                .then(bookRepository.deleteAll())
                .block();

            // Создаем авторов
            Author author1 = new Author(null, "Лев Толстой");
            Author author2 = new Author(null, "Федор Достоевский");

            // Создаем жанры
            Genre genre1 = new Genre(null, "Роман");
            Genre genre2 = new Genre(null, "Драма");

            // Сохраняем авторов и жанры
            Mono.zip(
                authorRepository.save(author1),
                authorRepository.save(author2),
                genreRepository.save(genre1),
                genreRepository.save(genre2)
            ).flatMap(tuple -> {
                Author savedAuthor1 = tuple.getT1();
                Author savedAuthor2 = tuple.getT2();
                Genre savedGenre1 = tuple.getT3();
                Genre savedGenre2 = tuple.getT4();

                // Создаем книги
                Book book1 = new Book(null, "Война и мир", savedAuthor1, savedGenre1);
                Book book2 = new Book(null, "Преступление и наказание", savedAuthor2, savedGenre2);

                return Mono.zip(
                    bookRepository.save(book1),
                    bookRepository.save(book2)
                );
            }).block();
        };
    }
} 