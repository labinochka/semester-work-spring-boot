package ru.kpfu.itis.beerokspring.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.kpfu.itis.beerokspring.exception.NoAccessException;
import ru.kpfu.itis.beerokspring.exception.NotFoundServiceException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({NoResourceFoundException.class, NotFoundServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final String notFoundExceptions() {
        return "view/error/notFound";
    }

    @ExceptionHandler(NoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final String noAccessExceptions() {
        return "view/error/noAccess";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final String onAllExceptions(Exception e) {
        log.error("Error:", e);
        return "view/error/serverError";
    }
}

