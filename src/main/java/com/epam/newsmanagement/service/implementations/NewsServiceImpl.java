package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public NewsServiceImpl(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    public NewsServiceImpl() {
    }

    @Override
    public long addNews(News news) throws ServiceException {
        try {
            return newsDAO.insert(news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) != null)
                newsDAO.update(news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) != null)
                newsDAO.delete(news);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News findById(long newsId) throws DAOException {
        return newsDAO.findById(newsId);
    }

    @Override
    public void addNewsAuthor(long newsId, long authorId) {
        newsDAO.insertNewsAuthor(newsId, authorId);
    }

    @Override
    public void addNewsTag(long newsId, long tagId) {
        newsDAO.insertNewsTag(newsId, tagId);
    }

    @Override
    public void addNewsTags(long newsId, List<Long> tagIdList) {
        newsDAO.insertNewsTags(newsId, tagIdList);
    }

    @Override
    public List<News> findNewsByTags(List<Tag> tags) {
        return newsDAO.findByTags(tags);
    }

    @Override
    public List<News> findAll() throws DAOException {
        return newsDAO.findAll();
    }

    @Override
    public List<News> findByAuthor(String authorName) {
        return newsDAO.findByAuthor(authorName);
    }

    @Override
    public List<News> findByTag(String tagName) {
        return newsDAO.findByTag(tagName);
    }

    @Override
    public List<News> findByTags(List<Tag> tags) {
        return newsDAO.findByTags(tags);
    }

}
