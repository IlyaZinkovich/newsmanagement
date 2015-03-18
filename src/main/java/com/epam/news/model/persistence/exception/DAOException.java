package com.epam.news.model.persistence.exception;

import com.epam.news.exception.NewsException;

public class DAOException extends NewsException {
    public DAOException() {
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
