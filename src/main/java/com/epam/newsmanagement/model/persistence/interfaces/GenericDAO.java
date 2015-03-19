package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface GenericDAO<T> {
    public int insert(T item) throws DAOException;
    public void update(T item) throws DAOException;
    public void delete(T item) throws DAOException;
}
