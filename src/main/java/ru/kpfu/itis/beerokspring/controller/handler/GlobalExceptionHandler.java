package ru.kpfu.itis.beerokspring.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.kpfu.itis.beerokspring.exception.NotFoundServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoResourceFoundException.class, NotFoundServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final String notFoundExceptions() {
        return "view/error/notFound";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final String onAllExceptions(Exception e) {
        return "view/error/serverError";
    }
}

