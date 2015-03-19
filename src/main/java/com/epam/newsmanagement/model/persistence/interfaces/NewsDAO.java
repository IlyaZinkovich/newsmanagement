package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface NewsDAO extends GenericDAO<News> {
    public News findById(int id);
    public List<News> findByAuthor(String authorName);
    public List<News> findByAuthor(int authorId);
    public List<News> findByTag(String tagName);
    public List<News> findByTag(int tagId);
    public List<News> findAll();

}
