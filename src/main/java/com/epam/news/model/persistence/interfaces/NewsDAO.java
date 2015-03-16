package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.News;

import java.util.List;

public interface NewsDAO {
    public News create();
    public void insert(News news);
    public void update(News news);
    public void delete(News news);
    public News findByID(int id);
    public List<News> findAll();
}
