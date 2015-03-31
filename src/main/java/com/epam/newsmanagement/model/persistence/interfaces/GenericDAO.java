package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.persistence.exception.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    long insert(T item) throws DAOException;
    void update(T item) throws DAOException;
    void delete(T item) throws DAOException;
    List<T> findAll() throws DAOException;
}
