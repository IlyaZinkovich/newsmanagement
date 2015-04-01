package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsManagementService {

    /**
     * Adds a news author to the data source.
     *
     * @param newsId
     *        Id of news
     *
     *  @param author
     *        The author to add
     *
     * @throws ServiceException in case of DAOException
     */
    void addNewsAuthor(long newsId, Author author) throws ServiceException;

    /**
     * Adds a list of news tags to the data source.
     *
     * @param newsId
     *        Id of news
     *
     * @param tags
     *        The list of tags to add
     *
     * @throws ServiceException in case of DAOException
     */
    void addNewsTags(long newsId, List<Tag> tags) throws ServiceException;

    /**
     * Adds a news tag to the data source.
     *
     * @param newsId
     *        Id of news
     *
     *  @param tag
     *        The tag to add
     *
     * @throws ServiceException in case of DAOException
     */
    void addNewsTag(long newsId, Tag tag) throws ServiceException;

    /**
     * Adds news with its author and tags to the data source.
     *
     * @param news
     *        News to add
     *
     * @param author
     *        News author
     *
     * @param tags
     *        News tags
     *
     * @throws ServiceException in case of DAOException
     */
    void addComplexNews(News news, Author author, List<Tag> tags) throws ServiceException;

    /**
     * Returns the news with its author and tags by its id.
     *
     * @param newsId
     *        Id of news to find
     *
     * @return the news with its author and tags by its id
     *
     * @throws ServiceException in case of DAOException
     */
    ComplexNews findComplexNewsById(long newsId) throws ServiceException;

}

