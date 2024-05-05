package ru.kpfu.itis.beerokspring.exception;

public class CommentNotFoundException extends NotFoundServiceException {

    public CommentNotFoundException() {
        super("Comment not found");
    }
}
