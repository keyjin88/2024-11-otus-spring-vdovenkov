package ru.vavtech.hw7.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.vavtech.hw7.models.Genre;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaGenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void returnGenreById() {
        var actualGenre = genreRepository.findById(1L);
        var expectedGenre = em.find(Genre.class, 1);
        assertThat(actualGenre)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    void loadAllGenre() {
        var genres = IntStream.range(1, 4).mapToObj(i -> new Genre(i, "Genre_" + i)).toList();
        assertThat(genreRepository.findAll())
                .containsExactlyElementsOf(genres);
    }
}
