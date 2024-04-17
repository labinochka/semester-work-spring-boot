package ru.kpfu.itis.beerokspring.exception;

public class AccountNotFoundException extends NotFoundServiceException {

    public AccountNotFoundException() {
        super("Account not found");
    }
}
