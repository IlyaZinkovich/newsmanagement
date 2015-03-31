package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.*;
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

    @Override
    public void addNews(News news) throws ServiceException {
        newsService.addNews(news);
    }

    @Override
    public void editNews(News news) throws ServiceException {
        newsService.editNews(news);
    }

    @Override
    public void deleteNews(News news) throws ServiceException {
        newsService.deleteNews(news);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsAuthor(long newsId, Author author) throws ServiceException {
        if (newsService.findById(newsId) != null) {
            long authorId = authorService.addAuthor(author);
            newsService.addNewsAuthor(newsId, authorId);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsTags(long newsId, List<Tag> tags) throws ServiceException {
        if (newsService.findById(newsId) != null) {
            List<Long> tagIdList = tagService.addTags(tags);
            newsService.addNewsTags(newsId, tagIdList);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void addNewsTag(long newsId, Tag tag) throws ServiceException {
        if (newsService.findById(newsId) != null) {
            long tagId = tagService.addTag(tag);
            newsService.addNewsTag(newsId, tagId);
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
    public List<News> findAllNews() {
        return newsService.findAll();
    }

    @Override
    public List<News> findNewsByAuthor(String authorName) {
        return newsService.findByAuthor(authorName);
    }

    @Override
    public List<News> findNewsByTag(String tagName) {
        return newsService.findByTag(tagName);
    }

    @Override
    public List<News> findNewsByTags(List<Tag> tags) {
        return newsService.findByTags(tags);
    }

    @Override
    public ComplexNews findComplexNewsById(long newsId) {
        News news = newsService.findById(newsId);
        Author author = authorService.findByNewsId(newsId);
        List<Tag> tags = tagService.findByNewsId(newsId);
        List<Comment> comments = commentService.findByNewsId(newsId);
        return new ComplexNews(news, author, tags, comments);
    }

    @Override
    public void addComment(Comment comment) throws ServiceException {
        if (newsService.findById(comment.getNewsId()) != null)
            commentService.addComment(comment);
    }

    @Override
    public void deleteComment(Comment comment) throws ServiceException {
        if (commentService.findById(comment.getId()) != null)
            commentService.delete(comment);
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
