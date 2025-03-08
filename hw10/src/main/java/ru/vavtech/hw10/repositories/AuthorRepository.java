package ru.vavtech.hw10.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw10.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
