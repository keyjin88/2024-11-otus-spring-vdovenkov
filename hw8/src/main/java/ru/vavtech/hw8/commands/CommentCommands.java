package ru.vavtech.hw8.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.vavtech.hw8.converters.CommentConverter;
import ru.vavtech.hw8.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Command(group = "Book comment commands")
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(key = "bcs", value = "Show comments for book")
    public String showComments(String bookId) {
        var comments = commentService.findByBookId(bookId)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.toList());
        if (comments.isEmpty()) {
            return "No comments available";
        }
        return String.join(System.lineSeparator(), comments);
    }

    @ShellMethod(key = "bcu", value = "Edit comment for book")
    public String updateComment(String commentId, String changedText) {
        var comment = commentService.updateComment(commentId, changedText);
        return commentConverter.commentToString(comment);
    }

    @ShellMethod(key = "bcd", value = "Delete comment for book")
    public void deleteComment(String commentId) {
        commentService.deleteCommentById(commentId);
    }

    @ShellMethod(key = "bcn", value = "Add new comment for book")
    public String addCommentForBook(String bookId, String commentText) {
        var comment = commentService.addComment(bookId, commentText);
        return commentConverter.commentToString(comment);
    }
}
