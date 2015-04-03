package com.epam.newsmanagement.model.persistence.oracle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The interface that represents the template methods for all DAO.
 *
 * @param <T> the type of domain retrieved from the data source
 *
 */
public interface DAOHelper<T> {

    /**
     * Returns the statement prepared for update of the object of {@code T} type.
     *
     * @param  connection
     *         The database connection
     *
     * @param  item
     *         The item to update
     *
     * @return  the {@code PreparedStatement} containing query filled with item's parameters
     *
     * @throws  SQLException
     */
    PreparedStatement prepareStatementForUpdate(Connection connection, T item) throws SQLException;

    /**
     * Returns the statement prepared for insert of the object of {@code T} type.
     *
     * @param  connection
     *         The database connection
     *
     * @param  item
     *         The item to insert
     *
     * @return  the {@code PreparedStatement} containing query filled with item's parameters
     *
     * @throws  SQLException
     */
    PreparedStatement prepareStatementForInsert(Connection connection, T item) throws SQLException;

    /**
     * Returns the statement prepared for delete of the object with the given id.
     *
     * @param  connection
     *         The database connection
     *
     * @param  itemId
     *         The id of item to insert
     *
     * @return  the {@code PreparedStatement} containing query filled with id of item to delete
     *
     * @throws  SQLException
     */
    PreparedStatement prepareStatementForDelete(Connection connection, long itemId) throws SQLException;

    /**
     * Returns the statement prepared for retrieving of object with the given id.
     *
     * @param  connection
     *         The database connection
     *
     * @param  itemId
     *         The id of item to retrieve
     *
     * @return  the {@code PreparedStatement} containing query filled with id of item to retrieve
     *
     * @throws  SQLException
     */
    PreparedStatement prepareStatementForFindById(Connection connection, long itemId) throws SQLException;

    /**
     * Returns the statement prepared for retrieving of all objects of {@code T} type.
     *
     * @param  connection
     *         The database connection
     *
     * @return  the {@code PreparedStatement} containing query to retrieve all the objects of {@code T} type
     *
     * @throws  SQLException
     */
    PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException;

    /**
     * Parses the {@code java.sql.ResultSet} into a list of {@code T} type objects.
     *
     * @param  resultSet
     *         The result set to parse
     *
     * @return  the list of {@code T} type objects
     *
     * @throws  SQLException
     */
    List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    /**
     * Returns the {@code javax.sql.DataSource} of current DAO
     *
     * @return  the {@code javax.sql.DataSource} of current DAO
     *
     */
    DataSource getDataSource();

}
