package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsManagementService {

    void addNews(News news) throws ServiceException;

    void editNews(News news) throws ServiceException;

    void deleteNews(News news) throws ServiceException;

    void addNewsAuthor(long newsId, Author author) throws ServiceException;

    void addNewsTags(long newsId, List<Tag> tags) throws ServiceException;

    void addNewsTag(long newsId, Tag tag) throws ServiceException;

    void addComplexNews(News news, Author author, List<Tag> tags) throws ServiceException;

    List<News> findAllNews();

    List<News> findNewsByAuthor(String authorName);

    List<News> findNewsByTag(String tagName);

    List<News> findNewsByTags(List<Tag> tags);

    ComplexNews findComplexNewsById(long newsId);

    void addComment(Comment comment) throws ServiceException;

    void deleteComment(Comment comment) throws ServiceException;
}

