package ru.vavtech.hw11.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw11.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
