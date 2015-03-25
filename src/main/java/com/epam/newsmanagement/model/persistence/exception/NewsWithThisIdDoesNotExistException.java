package com.epam.newsmanagement.model.persistence.exception;

public class NewsWithThisIdDoesNotExistException extends DAOException {
    public NewsWithThisIdDoesNotExistException() {

    }

    public NewsWithThisIdDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
