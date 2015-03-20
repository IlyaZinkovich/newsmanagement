package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface NewsDAO extends GenericDAO<News> {
    News findById(int id);
    List<News> findByAuthor(String authorName);
    List<News> findByAuthor(int authorId);
    List<News> findByTag(String tagName);
    List<News> findByTag(int tagId);
    List<News> findAll();
    List<News> findByTags(List<Tag> tags);
}
