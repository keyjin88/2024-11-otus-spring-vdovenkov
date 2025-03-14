package ru.vavtech.hw8.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.vavtech.hw8.converters.AuthorConverter;
import ru.vavtech.hw8.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
