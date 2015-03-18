package com.epam.news.exception;

public class NewsException extends Exception {
    public NewsException() {
    }

    public NewsException(String message, Throwable cause) {
        super(message, cause);
    }
}
