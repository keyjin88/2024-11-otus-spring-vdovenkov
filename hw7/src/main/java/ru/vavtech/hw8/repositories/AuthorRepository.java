package ru.vavtech.hw8.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw8.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
