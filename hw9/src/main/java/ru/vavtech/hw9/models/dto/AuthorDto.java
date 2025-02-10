package ru.vavtech.hw9.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vavtech.hw9.models.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    
    private Long id;
    private String fullName;

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
}
