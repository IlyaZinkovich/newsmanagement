package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsManagementService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ComplexNewsService complexNewsService;

    public void addNews(News news) {
        newsService.addNews(news);
    }

    public void addComplexNews(News news, Author author, List<Tag> tags) {
        complexNewsService.addComplexNews(news, author, tags);
    }

    public void deleteNews(News news) {
        newsService.deleteNews(news);
    }

    public List<News> findAllNews() {
        return newsService.findAll();
    }

    public News findNewsById(int newsId) {
        return newsService.findById(newsId);
    }

    public void addTags(List<Tag> tags, int newsId) {
        tagsService.addNewsTags(tags, newsId);
    }

    public List<News> findNewsByTags(List<Tag> tags) {
        return newsService.findByTags(tags);
    }

    public void addComment(Comment comment) {
        commentService.addComment(comment);
    }

    public void deleteComment(Comment comment) {
        commentService.deleteComment(comment);
    }

}
