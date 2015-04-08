package com.epam.newsmanagement.dao;

import com.epam.newsmanagement.domain.Author;
import com.epam.newsmanagement.dao.exception.DAOException;

import java.util.Date;

/**
 * A DatabaseAccessObject interface that
 * provides access to authors data in the data source.
 */
public interface AuthorDAO extends GenericDAO<Author> {

    /**
     * Returns the {@code Author} object
     * representing the author that news with the given id has.
     *
     * @param  newsId
     *         The news id
     *
     * @return  the {@code Author} object representing the author
     * that news with the given id has
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    Author findByNewsId(long newsId) throws DAOException;

    /**
     * Returns the {@code Author} object with the given name.
     *
     * @param  authorName
     *         The Author name
     *
     * @return  the {@code Author} object with the given name
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    Author findByName(String authorName) throws DAOException;

    /**
     * Updates the author expiration date.
     *
     * @param  authorId
     *         The Author id
     *
     * @param  expirationDate
     *         The Author expiration date
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    void update(long authorId, Date expirationDate) throws DAOException;
}
