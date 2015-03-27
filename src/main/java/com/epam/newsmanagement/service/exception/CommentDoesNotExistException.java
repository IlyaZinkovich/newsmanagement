package com.epam.newsmanagement.service.exception;

public class CommentDoesNotExistException extends ServiceException {
    public CommentDoesNotExistException() {
    }

    public CommentDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
