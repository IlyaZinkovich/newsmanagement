package com.epam.news.service;

import com.epam.news.model.entity.Author;
import com.epam.news.model.entity.News;
import com.epam.news.model.entity.Tag;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.interfaces.NewsAuthorDAO;
import com.epam.news.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.persistence.interfaces.NewsTagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private NewsTagDAO newsTagDAO;

    @Autowired
    private NewsAuthorDAO newsAuthorDAO;

    public void addNews(News news) {
        try {
            newsDAO.insert(news);
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

    public void addNews(News news, Author author, List<Tag> tags) {
        addNews(news);
        try {
            news = newsDAO.findLastInserted();
            newsTagDAO.insert(tags, news.getId());
            newsAuthorDAO.insert(author.getName(), news.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
