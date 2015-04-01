package com.epam.newsmanagement.model.persistence.exception;

import com.epam.newsmanagement.exception.NewsManagementException;

public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
