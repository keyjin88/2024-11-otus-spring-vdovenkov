package ru.vavtech.hw11.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;

    private String name;
}
