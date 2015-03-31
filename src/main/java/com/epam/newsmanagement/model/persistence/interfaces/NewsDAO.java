package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface NewsDAO extends GenericDAO<News> {
    void insertNewsAuthor(long newsId, long authorId);
    void insertNewsTag(long newsId, long tagId);
    void insertNewsTags(long newsId, List<Long> tagIdList);
    News findById(long id);
    List<News> findByAuthor(String authorName);
    List<News> findByAuthor(long authorId);
    List<News> findByTag(String tagName);
    List<News> findByTag(long tagId);
    List<News> findAll();
    List<News> findByTags(List<Tag> tags);
}
