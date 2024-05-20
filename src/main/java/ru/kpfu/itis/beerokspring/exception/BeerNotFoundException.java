package ru.kpfu.itis.beerokspring.exception;

public class BeerNotFoundException extends NotFoundServiceException{

    public BeerNotFoundException() {
        super("Beer not found");
    }
}
