package com.epam.news.model.persistence.exception;

public class NewsWithThisIdDoesNotExistException extends DAOException {
    public NewsWithThisIdDoesNotExistException() {

    }

    public NewsWithThisIdDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
