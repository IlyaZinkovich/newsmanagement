package com.epam.newsmanagement.service.exception;

import com.epam.newsmanagement.exception.NewsManagementException;

public class ServiceException extends NewsManagementException {
    public ServiceException() {
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
