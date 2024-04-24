package ru.kpfu.itis.beerokspring.exception;

public class TokenNotFoundException extends NotFoundServiceException {
    public TokenNotFoundException() {
        super("Token not found");
    }
}
