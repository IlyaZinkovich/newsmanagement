package com.epam.newsmanagement.service.exception;

public class NewsNotFoundException extends ServiceException {
    public NewsNotFoundException() {
    }

    public NewsNotFoundException(Throwable cause) {
        super(cause);
    }
}
