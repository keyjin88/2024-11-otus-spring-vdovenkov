package ru.vavtech.hw11.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vavtech.hw11.model.Author;
import ru.vavtech.hw11.model.Book;
import ru.vavtech.hw11.model.Genre;
import ru.vavtech.hw11.repository.AuthorRepository;
import ru.vavtech.hw11.repository.BookRepository;
import ru.vavtech.hw11.repository.GenreRepository;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(AuthorRepository authorRepository,
                                        GenreRepository genreRepository,
                                        BookRepository bookRepository) {
        return args -> {
            // Удаляем все существующие данные
            Mono.when(
                authorRepository.deleteAll(),
                genreRepository.deleteAll(),
                bookRepository.deleteAll()
            ).block();

            // Создаем авторов
            var tolstoy = new Author("1", "Лев Толстой");
            var dostoevsky = new Author("2", "Федор Достоевский");
            var bulgakov = new Author("3", "Михаил Булгаков");

            // Создаем жанры
            var novel = new Genre("1", "Роман");
            var fantasy = new Genre("2", "Фантастика");
            var classic = new Genre("3", "Классика");

            // Создаем книги
            Flux.just(
                new Book("1", "Война и мир", tolstoy, novel),
                new Book("2", "Преступление и наказание", dostoevsky, novel),
                new Book("3", "Мастер и Маргарита", bulgakov, fantasy),
                new Book("4", "Анна Каренина", tolstoy, classic)
            ).flatMap(bookRepository::save).collectList().block();
        };
    }
} 