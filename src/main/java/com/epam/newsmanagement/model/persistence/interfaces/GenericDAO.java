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
    List<T> findAll();
    PreparedStatement prepareStatementForUpdate(Connection connection, T item) throws SQLException;
    PreparedStatement prepareStatementForInsert(Connection connection, T item) throws SQLException;
    PreparedStatement prepareStatementForDelete(Connection connection, T item) throws SQLException;
    PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException;
    PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException;
    List<T> parseResultSet(ResultSet resultSet) throws SQLException;
    DataSource getDataSource();
}
