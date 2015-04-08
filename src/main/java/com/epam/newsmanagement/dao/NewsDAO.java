package com.epam.newsmanagement.dao;

import com.epam.newsmanagement.domain.News;
import com.epam.newsmanagement.domain.Tag;
import com.epam.newsmanagement.dao.exception.DAOException;

import java.util.List;

/**
 * A DatabaseAccessObject interface that
 * provides access to news data in the data source.
 */
public interface NewsDAO extends GenericDAO<News> {

    /**
     * Creates a link between the news and its author.
     *
     * @param  newsId
     *         The news id
     *
     * @param  authorId
     *         The id of news author
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    void insertNewsAuthor(long newsId, long authorId) throws DAOException;

    /**
     * Creates a link between the news and its tag.
     *
     * @param  newsId
     *         The news id
     *
     * @param  tagId
     *         The id of news tag
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    void insertNewsTag(long newsId, long tagId) throws DAOException;

    /**
     * Creates a link between the news and its tags.
     *
     * @param  newsId
     *         The news id
     *
     * @param  tagIdList
     *         The list of news tag id's
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    void insertNewsTags(long newsId, List<Long> tagIdList) throws DAOException;

    /**
     * Returns the list of news which have the author with the specified name.
     *
     * @param  authorName
     *         The Author name
     *
     * @return  the list of news which have the author with the specified name
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<News> findByAuthor(String authorName) throws DAOException;

    /**
     * Returns the list of news which have the author with the specified id.
     *
     * @param  authorId
     *         The Author id
     *
     * @return  the list of news which have the author with the specified id
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<News> findByAuthor(long authorId) throws DAOException;

    /**
     * Returns the list of news which have the tag with the specified name.
     *
     * @param  tagName
     *         The Tag name
     *
     * @return  the list of news which have the tag with the specified name
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<News> findByTag(String tagName) throws DAOException;

    /**
     * Returns the list of news which have the tag with the specified id.
     *
     * @param  tagId
     *         The Tag id
     *
     * @return  the list of news which have the tag with the specified id
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<News> findByTag(long tagId) throws DAOException;

    /**
     * Returns the list of news which have the given tags.
     *
     * @param  tags
     *         The list of tags
     *
     * @return  the list of news which have the given tags
     *
     * @throws  DAOException
     *          In case of {@code SQLException}
     */
    List<News> findByTags(List<Tag> tags) throws DAOException;
}
