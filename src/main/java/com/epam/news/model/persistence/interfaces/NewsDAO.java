package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.News;

import java.util.List;

public interface NewsDAO {
    public void insert(News news);
    public void update(News news);
    public void delete(News news);
    public News findById(int id);
    public List<News> findByAuthorName(String authorName);
    public List<News> findByTagName(String tagName);
    public List<News> findAll();
}
