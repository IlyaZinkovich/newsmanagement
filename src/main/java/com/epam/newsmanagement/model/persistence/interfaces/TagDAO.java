package com.epam.newsmanagement.model.persistence.interfaces;


import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

/**
 * A DatabaseAccessObject interface that
 * provides access to tags data in the data source.
 */
public interface TagDAO extends GenericDAO<Tag> {

    /**
     * Inserts the given tags into a data source
     * and returns the list of generated id's.
     *
     * @param  tags
     *         The list of tags
     *
     * @return  the list of generated id's
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<Long> insert(List<Tag> tags) throws DAOException;

    /**
     * Returns the list of tags
     * that news with the given id has.
     *
     * @param  newsId
     *         The news id
     *
     * @return  the list of tags
     * that news with the given id has
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<Tag> findByNewsId(long newsId) throws DAOException;

    /**
     * Returns the {@code Tag} object with the given name.
     *
     * @param  tagName
     *         The tag name
     *
     * @return  the {@code Tag} object with the given name
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    Tag findByName(String tagName) throws DAOException;
}
