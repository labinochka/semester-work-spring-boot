package ru.kpfu.itis.beerokspring.exception;

import org.springframework.http.HttpStatus;

public class NoAccessException extends ServiceException{

    public NoAccessException() {
        super("Нет доступа к действию", HttpStatus.FORBIDDEN);
    }
}
