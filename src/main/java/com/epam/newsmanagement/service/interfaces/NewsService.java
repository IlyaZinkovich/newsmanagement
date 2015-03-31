package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

public interface NewsService {

    long addNews(News news) throws ServiceException;

    void editNews(News news) throws ServiceException;

    void deleteNews(News news) throws ServiceException;

    News findById(long newsId) throws DAOException;

    void addNewsAuthor(long newsId, long authorId);

    void addNewsTag(long newsId, long tagId);

    void addNewsTags(long newsId, List<Long> tagIdList);

    List<News> findNewsByTags(List<Tag> tags);

    List<News> findAll() throws DAOException;

    List<News> findByAuthor(String authorName);

    List<News> findByTag(String tagName);

    List<News> findByTags(List<Tag> tags);

}
