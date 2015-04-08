package com.epam.newsmanagement.dao;


import com.epam.newsmanagement.domain.Comment;
import com.epam.newsmanagement.dao.exception.DAOException;

import java.util.List;

/**
 * A DatabaseAccessObject interface that
 * provides access to comments data in the data source.
 */
public interface CommentDAO extends GenericDAO<Comment> {

    /**
     * Inserts the given comments into a data source
     * and returns the list of generated id's.
     *
     * @param  comments
     *         The list of comments
     *
     * @return  the list of generated id's
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<Long> insert(List<Comment> comments) throws DAOException;

    /**
     * Returns the list of comments
     * that news with the given id has.
     *
     * @param  newsId
     *         The news id
     *
     * @return  the list of comments
     * that news with the given id has
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<Comment> findByNewsId(long newsId) throws DAOException;
}
