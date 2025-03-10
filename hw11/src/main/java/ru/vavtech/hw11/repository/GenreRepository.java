package ru.vavtech.hw11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw11.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
