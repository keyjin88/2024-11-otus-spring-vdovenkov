package ru.vavtech.hw5.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.vavtech.hw5.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {
    private final NamedParameterJdbcOperations namedJdbc;

    @Override
    public List<Author> findAll() {
        return namedJdbc.query("SELECT id, full_name FROM authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        var authorList = namedJdbc.query(
                "SELECT id, full_name FROM authors WHERE id = :id", Map.of("id", id), new AuthorRowMapper());
        return authorList.isEmpty() ? Optional.empty() : Optional.of(authorList.get(0));
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            var id = rs.getLong("id");
            var fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}