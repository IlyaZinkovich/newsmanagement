package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.domain.Tag;
import com.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

public interface TagService {

    /**
     * Adds tag to the data source and returns the generated id.
     *
     * @param tag
     *        Tag to add
     *
     * @return generated id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    long addTag(Tag tag) throws ServiceException;

    /**
     * Adds list of tags to the data source and returns the generated ids.
     *
     * @param tags
     *        Tags to add
     *
     * @return generated ids
     *
     * @throws ServiceException in case of DAOException
     *
     */
    List<Long> addTags(List<Tag> tags) throws ServiceException;

    /**
     * Edits the information about tag in the data source.
     *
     * @param tag
     *        tag to edit
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void editTag(Tag tag) throws ServiceException;

    /**
     * Deletes tag with the given id from the data source.
     *
     * @param tagId
     *        Id of the tag to delete
     *
     * @throws ServiceException in case of DAOException
     *
     */
    void deleteTag(long tagId) throws ServiceException;

    /**
     * Returns the list of tags to news with the given id.
     *
     * @param newsId
     *        Id of the news to find by
     *
     * @return the list of tags to news with the given id
     *
     * @throws ServiceException in case of DAOException
     *
     */
    List<Tag> findByNewsId(long newsId) throws ServiceException;
}
