package com.epam.newsmanagement.service.exception;

public class NewsDoesNotExistException extends ServiceException {
    public NewsDoesNotExistException() {
    }

    public NewsDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
