package com.epam.newsmanagement.exception;

public class NewsException extends Exception {
    public NewsException() {
    }

    public NewsException(String message, Throwable cause) {
        super(message, cause);
    }
}
