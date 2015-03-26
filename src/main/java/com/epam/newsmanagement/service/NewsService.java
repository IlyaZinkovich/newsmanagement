package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.epam.newsmanagement.service.exception.NewsAlreadyExistsException;
import com.epam.newsmanagement.service.exception.NewsDoesNotExistException;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private CommentDAO commentDAO;

    public void addNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) == null)
                newsDAO.insert(news);
            else throw new NewsAlreadyExistsException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void editNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) != null)
                newsDAO.update(news);
            else throw new NewsDoesNotExistException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteNews(News news) throws ServiceException {
        try {
            if (newsDAO.findById(news.getId()) != null)
                newsDAO.delete(news);
            else throw new NewsDoesNotExistException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addNewsAuthor(int newsId, Author author) throws ServiceException {
        try {
            if (newsDAO.findById(newsId) != null) {
                int authorId = authorDAO.insert(author);
                newsDAO.insertNewsAuthor(newsId, authorId);
            } else throw new NewsDoesNotExistException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addNewsTags(int newsId, List<Tag> tags) throws ServiceException {
        try {
            List<Integer> tagIdList = tagDAO.insert(tags);
            newsDAO.insertNewsTags(newsId, tagIdList);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addComplexNews(News news, Author author, List<Tag> tags) throws ServiceException {
        try {
            int newsId = newsDAO.insert(news);
            int authorId = authorDAO.insert(author);
            newsDAO.insertNewsAuthor(newsId, authorId);
            List<Integer> tagIdList =  tagDAO.insert(tags);
            for (int tagId : tagIdList) newsDAO.insertNewsTag(newsId, tagId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
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

    public ComplexNews findComplexNewsById(int newsId) {
        News news = newsDAO.findById(newsId);
        Author author = authorDAO.findByNewsId(newsId);
        List<Tag> tags = tagDAO.findByNewsId(newsId);
        List<Comment> comments = commentDAO.findByNewsId(newsId);
        return new ComplexNews(news, author, tags, comments);
    }

    public void addComment(Comment comment) throws ServiceException {
        try {
            if (newsDAO.findById(comment.getNewsId()) != null)
                commentDAO.insert(comment);
            else throw new NewsDoesNotExistException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteComment(Comment comment) throws ServiceException {
        try {
            commentDAO.delete(comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
