package com.epam.newsmanagement.service;

import com.epam.newsmanagement.domain.Comment;
import com.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

public interface CommentService {

    /**
     * Adds comment to the data source and returns the generated id.
     *
     * @param comment
     *        Comment to add
     *
     * @return generated id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    long addComment(Comment comment) throws ServiceException;

    /**
     * Adds list of comment to the data source and returns the generated ids.
     *
     * @param comments
     *        Comments to add
     *
     * @return generated ids
     *
     * @throws ServiceException in case of DAOException
     *
     */
    List<Long> addComments(List<Comment> comments) throws ServiceException;

    /**
     * Edits the information about comment in the data source.
     *
     * @param comment
     *        Comment to edit
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void editComment(Comment comment) throws ServiceException;

    /**
     * Deletes comment with the given id from the data source.
     *
     * @param commentId
     *        Id of the comment to delete
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void deleteComment(long commentId) throws ServiceException;

    /**
     * Returns the list of comments to news with the given id.
     *
     * @param newsId
     *        Id of the news to find by
     *
     * @return the list of comments to news with the given id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    List<Comment> findByNewsId(long newsId) throws ServiceException;


    /**
     * Returns the comment with the given id.
     *
     * @param commentId
     *        Id of the comment
     *
     * @return comment with the given id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    Comment findById(long commentId) throws ServiceException;
}
