package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

public interface TagService {

    long addTag(Tag tag) throws ServiceException;

    List<Long> addTags(List<Tag> tags) throws ServiceException;

    List<Tag> findByNewsId(long newsId);
}
