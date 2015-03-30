package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsManagementService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    public void addNews(News news) throws ServiceException {
        newsService.addNews(news);
    }

    public void editNews(News news) throws ServiceException {
        newsService.editNews(news);
    }

    public void deleteNews(News news) throws ServiceException {
        newsService.deleteNews(news);
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addNewsAuthor(int newsId, Author author) throws ServiceException {
        if (newsService.findById(newsId) != null) {
            int authorId = authorService.addAuthor(author);
            newsService.addNewsAuthor(newsId, authorId);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addNewsTags(int newsId, List<Tag> tags) throws ServiceException {
        if (newsService.findById(newsId) != null) {
            List<Integer> tagIdList = tagService.addTags(tags);
            newsService.addNewsTags(newsId, tagIdList);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addNewsTag(int newsId, Tag tag) throws ServiceException {
        if (newsService.findById(newsId) != null) {
            int tagId = tagService.addTag(tag);
            newsService.addNewsTag(newsId, tagId);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void addComplexNews(News news, Author author, List<Tag> tags) throws ServiceException {
        int newsId = newsService.addNews(news);
        int authorId = authorService.addAuthor(author);
        newsService.addNewsAuthor(newsId, authorId);
        List<Integer> tagIdList =  tagService.addTags(tags);
        newsService.addNewsTags(newsId, tagIdList);
    }

    public List<News> findAllNews() {
        return newsService.findAll();
    }

    public List<News> findNewsByAuthor(String authorName) {
        return newsService.findByAuthor(authorName);
    }

    public List<News> findNewsByTag(String tagName) {
        return newsService.findByTag(tagName);
    }

    public List<News> findNewsByTags(List<Tag> tags) {
        return newsService.findByTags(tags);
    }

    public ComplexNews findComplexNewsById(int newsId) {
        News news = newsService.findById(newsId);
        Author author = authorService.findByNewsId(newsId);
        List<Tag> tags = tagService.findByNewsId(newsId);
        List<Comment> comments = commentService.findByNewsId(newsId);
        return new ComplexNews(news, author, tags, comments);
    }

    public void addComment(Comment comment) throws ServiceException {
        if (newsService.findById(comment.getNewsId()) != null)
            commentService.addComment(comment);
    }

    public void deleteComment(Comment comment) throws ServiceException {
        if (commentService.findById(comment.getId()) != null)
            commentService.delete(comment);
    }

    public NewsManagementService(NewsService newsService, AuthorService authorService, TagService tagService, CommentService commentService) {
        this.newsService = newsService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    public NewsManagementService() {
    }
}
