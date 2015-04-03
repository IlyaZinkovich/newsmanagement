package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.domain.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsManagementServiceImpl implements com.epam.newsmanagement.service.interfaces.NewsManagementService {

    private static Logger logger = Logger.getLogger(NewsManagementServiceImpl.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    public NewsManagementServiceImpl() {
    }

    public NewsManagementServiceImpl(NewsService newsService, AuthorService authorService, TagService tagService, CommentService commentService) {
        this.newsService = newsService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsAuthor(long newsId, Author author) throws ServiceException {
        try {
            if (newsService.findById(newsId) != null) {
                long authorId = authorService.addAuthor(author);
                newsService.addNewsAuthor(newsId, authorId);
            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsTags(long newsId, List<Tag> tags) throws ServiceException {
        try {
            if (newsService.findById(newsId) != null) {
                List<Long> tagIdList = tagService.addTags(tags);
                newsService.addNewsTags(newsId, tagIdList);
            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsTag(long newsId, Tag tag) throws ServiceException {
        try {
            if (newsService.findById(newsId) != null) {
                long tagId = tagService.addTag(tag);
                newsService.addNewsTag(newsId, tagId);
            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addComplexNews(News news, Author author, List<Tag> tags) throws ServiceException {
        long newsId = newsService.addNews(news);
        long authorId = authorService.addAuthor(author);
        newsService.addNewsAuthor(newsId, authorId);
        List<Long> tagIdList =  tagService.addTags(tags);
        newsService.addNewsTags(newsId, tagIdList);
    }

    @Override
    public ComplexNews findComplexNewsById(long newsId) throws ServiceException {
        ComplexNews complexNews = new ComplexNews();
        try {
            News news = newsService.findById(newsId);
            Author author = authorService.findByNewsId(newsId);
            List<Tag> tags = tagService.findByNewsId(newsId);
            List<Comment> comments = commentService.findByNewsId(newsId);
            complexNews.setNews(news);
            complexNews.setAuthor(author);
            complexNews.setTags(tags);
            complexNews.setComments(comments);
            return complexNews;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
