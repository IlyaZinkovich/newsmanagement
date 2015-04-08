package com.epam.newsmanagement.service;

import com.epam.newsmanagement.domain.News;
import com.epam.newsmanagement.domain.Tag;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

public interface NewsService {

    /**
     * Adds news to the data source and returns the generated id.
     *
     * @param news
     *        News to add
     *
     * @return generated id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    long addNews(News news) throws ServiceException;

    /**
     * Edits the information about news in the data source.
     *
     * @param news
     *        News to edit
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void editNews(News news) throws ServiceException;

    /**
     * Deletes news with the given id from the data source.
     *
     * @param newsId
     *        Id of the news to delete
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void deleteNews(long newsId) throws ServiceException;

    /**
     * Returns the news by the given id.
     *
     * @param newsId
     *        Id of the news
     *
     * @return the author by the id of his news
     *
     */
    News findById(long newsId) throws DAOException;

    /**
     * Creates a link between the news and its author.
     *
     * @param  newsId
     *         The news id
     *
     * @param  authorId
     *         The id of news author
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    void addNewsAuthor(long newsId, long authorId) throws ServiceException;

    /**
     * Creates a link between the news and its tag.
     *
     * @param  newsId
     *         The news id
     *
     * @param  tagId
     *         The id of news tag
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    void addNewsTag(long newsId, long tagId) throws ServiceException;

    /**
     * Creates a link between the news and its tags.
     *
     * @param  newsId
     *         The news id
     *
     * @param  tagIdList
     *         The list of news tag id's
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    void addNewsTags(long newsId, List<Long> tagIdList) throws ServiceException;

    /**
     * Returns the list of news which have the given tags.
     *
     * @param  tags
     *         The list of tags
     *
     * @return  the list of news which have the given tags
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    List<News> findNewsByTags(List<Tag> tags) throws ServiceException;

    /**
     * Returns the list of all the news.
     *
     * @return  the list of all the news
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    List<News> findAll() throws ServiceException;

    /**
     * Returns the list of news which have the author with the specified name.
     *
     * @param  authorName
     *         The Author name
     *
     * @return  the list of news which have the author with the specified name
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    List<News> findByAuthor(String authorName) throws ServiceException;

    /**
     * Returns the list of news which have the tag with the specified name.
     *
     * @param  tagName
     *         The Tag name
     *
     * @return  the list of news which have the tag with the specified name
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    List<News> findByTag(String tagName) throws ServiceException;

    /**
     * Returns the list of news which have the given tags.
     *
     * @param  tags
     *         The list of tags
     *
     * @return  the list of news which have the given tags
     *
     * @throws  ServiceException
     *          In case of {@code DAOException}
     */
    List<News> findByTags(List<Tag> tags) throws ServiceException;

}
