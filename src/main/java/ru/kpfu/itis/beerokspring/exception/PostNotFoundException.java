package ru.kpfu.itis.beerokspring.exception;

import java.util.UUID;

public class PostNotFoundException extends NotFoundServiceException {

    public PostNotFoundException() {
        super("Post not found");
    }

}
