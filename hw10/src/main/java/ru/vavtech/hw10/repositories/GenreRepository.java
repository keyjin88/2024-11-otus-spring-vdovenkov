package ru.vavtech.hw10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw10.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
