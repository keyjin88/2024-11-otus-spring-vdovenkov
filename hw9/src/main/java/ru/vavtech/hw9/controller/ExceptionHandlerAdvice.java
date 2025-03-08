package ru.vavtech.hw9.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vavtech.hw9.exceptions.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleNotFoundException(EntityNotFoundException ex) {
        log.error("Requested entity not found: {}", ex.getMessage());
        return "error/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        log.error("Internal server error: ", ex);
        return "error/500";
    }
} 