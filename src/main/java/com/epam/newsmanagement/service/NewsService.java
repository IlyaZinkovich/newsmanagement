package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public NewsService(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    public NewsService() {
    }

    public int addNews(News news) throws ServiceException {
        try {
            return newsDAO.insert(news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void editNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) != null)
                newsDAO.update(news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) != null)
                newsDAO.delete(news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public News findById(int newsId) {
        return newsDAO.findById(newsId);
    }

    public void addNewsAuthor(int newsId, int authorId) {
        newsDAO.insertNewsAuthor(newsId, authorId);
    }

    public void addNewsTag(int newsId, int tagId) {
        newsDAO.insertNewsTag(newsId, tagId);
    }

    public void addNewsTags(int newsId, List<Integer> tagIdList) {
        newsDAO.insertNewsTags(newsId, tagIdList);
    }

    public List<News> findNewsByTags(List<Tag> tags) {
        return newsDAO.findByTags(tags);
    }

    public List<News> findAll() {
        return newsDAO.findAll();
    }

    public List<News> findByAuthor(String authorName) {
        return newsDAO.findByAuthor(authorName);
    }

    public List<News> findByTag(String tagName) {
        return newsDAO.findByTag(tagName);
    }

    public List<News> findByTags(List<Tag> tags) {
        return newsDAO.findByTags(tags);
    }

}
