package com.epam.newsmanagement.dao;

import com.epam.newsmanagement.dao.exception.DAOException;

import java.util.List;

/**
 * The interface that represents the common methods for all DAO.
 *
 * @param <T> the type of entity retrieved from the data source
 *
 */
public interface GenericDAO<T> {

    /**
     * Inserts the given item into a data source
     * and returns the generated id.
     *
     * @param  item
     *         The item to be inserted
     *
     * @return  the generated id
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    long insert(T item) throws DAOException;

    /**
     * Updates the information about the given item in the data source.
     *
     * @param  item
     *         The item to be inserted
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    void update(T item) throws DAOException;

    /**
     * Deletes the {@code T} object with the given id from the data source.
     *
     * @param  itemId
     *         The id of item to be deleted
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    void delete(long itemId) throws DAOException;

    /**
     * Returns the list of all the object of {@code T} type found in the data source.
     *
     * @return  the list of all the object of {@code T} type found in the data source
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<T> findAll() throws DAOException;

    /**
     * Returns the {@code T} object with the given id found in the data source.
     *
     * @param  id
     *         The id of {@code T} object
     *
     * @return  the {@code T} object with the given id found in the data source
     *
     */
    T findById(long id) throws DAOException;
}
