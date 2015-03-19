package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public void addNews(News news) {
        try {
            int newsId = newsDAO.insert(news);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void editNews(News news) {
        try {
            newsDAO.update(news);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNews(News news) {
        try {
            newsDAO.delete(news);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public List<News> findAll() {
        return newsDAO.findAll();
    }

    public News findById(int id) {
        return newsDAO.findById(id);
    }

    public List<News> findByAuthor(String authorName) {
        return newsDAO.findByAuthor(authorName);
    }

    public List<News> findByAuthor(int authorId) {
        return newsDAO.findByAuthor(authorId);
    }

    public List<News> findByTag(String tagName) {
        return newsDAO.findByTag(tagName);
    }

    public List<News> findByTag(int tagId) {
        return newsDAO.findByTag(tagId);
    }

}
