package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.domain.Author;
import com.epam.newsmanagement.service.exception.ServiceException;

public interface AuthorService {

    /**
     * Adds author to the data source and returns the generated id.
     *
     * @param author
     *        Author to add
     *
     * @return generated id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    long addAuthor(Author author) throws ServiceException;

    /**
     * Edits the information about author in the data source.
     *
     * @param author
     *        Author to edit
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void editAuthor(Author author) throws ServiceException;

    /**
     * Deletes author with the given id from the data source.
     *
     * @param authorId
     *        Id of the author to delete
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void deleteAuthor(long authorId) throws ServiceException;

    /**
     * Returns the author by the id of his news.
     *
     * @param newsId
     *        Id of the news to find by
     *
     * @return the author by the id of his news
     *
     * @throws ServiceException in case of DAOException
     *
     */
    Author findByNewsId(long newsId) throws ServiceException;

    /**
     * Returns the author with the given id.
     *
     * @param authorId
     *        Id of the author
     *
     * @return the author with with the given id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    Author findById(long authorId) throws ServiceException;
}