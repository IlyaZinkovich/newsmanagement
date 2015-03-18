package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.News;
import com.epam.news.model.persistence.exception.DAOException;

import java.util.List;

public interface NewsDAO {
    public void insert(News news) throws DAOException;
    public void update(News news) throws DAOException;
    public void delete(News news) throws DAOException;
    public News findById(int id);
    public List<News> findByAuthor(String authorName);
    public List<News> findByAuthor(int authorId);
    public List<News> findByTag(String tagName);
    public List<News> findByTag(int tagId);
    public List<News> findAll();
}
