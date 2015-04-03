package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.domain.News;
import com.epam.newsmanagement.model.domain.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private static Logger logger = Logger.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsDAO newsDAO;

    public NewsServiceImpl() {
    }

    public NewsServiceImpl(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    @Override
    public long addNews(News news) throws ServiceException {
        try {
            return newsDAO.insert(news);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void editNews(News news) throws ServiceException {
        try {
            newsDAO.update(news);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteNews(long newsId) throws ServiceException {
        try {
            newsDAO.delete(newsId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public News findById(long newsId) throws DAOException {
        return newsDAO.findById(newsId);
    }

    @Override
    public void addNewsAuthor(long newsId, long authorId) throws ServiceException {
        try {
            newsDAO.insertNewsAuthor(newsId, authorId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNewsTag(long newsId, long tagId) throws ServiceException {
        try {
            newsDAO.insertNewsTag(newsId, tagId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNewsTags(long newsId, List<Long> tagIdList) throws ServiceException {
        try {
            newsDAO.insertNewsTags(newsId, tagIdList);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findNewsByTags(List<Tag> tags) throws ServiceException {
        try {
            return newsDAO.findByTags(tags);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findAll() throws ServiceException {
        try {
            return newsDAO.findAll();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findByAuthor(String authorName) throws ServiceException {
        try {
            return newsDAO.findByAuthor(authorName);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findByTag(String tagName) throws ServiceException {
        try {
            return newsDAO.findByTag(tagName);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findByTags(List<Tag> tags) throws ServiceException {
        try {
            return newsDAO.findByTags(tags);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

}
