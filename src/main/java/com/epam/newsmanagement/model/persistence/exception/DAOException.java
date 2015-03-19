package com.epam.newsmanagement.model.persistence.exception;

import com.epam.newsmanagement.exception.NewsException;

public class DAOException extends NewsException {
    public DAOException() {
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
