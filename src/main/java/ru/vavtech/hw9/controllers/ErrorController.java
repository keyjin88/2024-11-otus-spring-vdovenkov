package ru.vavtech.hw9.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.vavtech.hw9.exceptions.NotFoundException;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NotFoundException e) {
        log.error("Resource not found: {}", e.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("message", e.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception e) {
        log.error("Unexpected error occurred", e);
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("message", "Произошла внутренняя ошибка сервера");
        return mav;
    }
} 