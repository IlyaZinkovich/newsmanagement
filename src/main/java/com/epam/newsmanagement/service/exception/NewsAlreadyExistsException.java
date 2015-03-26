package com.epam.newsmanagement.service.exception;

public class NewsAlreadyExistsException extends ServiceException {
    public NewsAlreadyExistsException() {
    }

    public NewsAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
