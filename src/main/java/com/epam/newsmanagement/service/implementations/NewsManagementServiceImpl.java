package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsManagementServiceImpl implements NewsManagementService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsAuthor(long newsId, Author author) throws ServiceException {
        try {
            if (newsService.findById(newsId) != null) {
                long authorId = authorService.addAuthor(author);
                newsService.addNewsAuthor(newsId, authorId);
            }
        } catch (DAOException e) {
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
        News news = null;
        try {
            news = newsService.findById(newsId);
            Author author = authorService.findByNewsId(newsId);
            List<Tag> tags = tagService.findByNewsId(newsId);
            List<Comment> comments = commentService.findByNewsId(newsId);
            return new ComplexNews(news, author, tags, comments);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public NewsManagementServiceImpl(NewsService newsService, AuthorService authorService, TagService tagService, CommentService commentService) {
        this.newsService = newsService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    public NewsManagementServiceImpl() {
    }
}
