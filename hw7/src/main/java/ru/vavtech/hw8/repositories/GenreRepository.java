package ru.vavtech.hw8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw8.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
