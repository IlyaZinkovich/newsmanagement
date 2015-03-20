package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface GenericDAO<T> {
    int insert(T item) throws DAOException;
    void update(T item) throws DAOException;
    void delete(T item) throws DAOException;
}
