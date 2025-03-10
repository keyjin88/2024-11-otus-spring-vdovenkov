package ru.vavtech.hw11.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.vavtech.hw11.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(long bookId);
}
