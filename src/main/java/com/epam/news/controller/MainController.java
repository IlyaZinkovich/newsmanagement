package com.epam.news.controller;


import com.epam.news.model.entity.Author;
import com.epam.news.model.entity.Comment;
import com.epam.news.model.entity.News;
import com.epam.news.model.entity.Tag;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
        NewsService newsService = (NewsService) context.getBean("newsService");
        List<News> newsList = newsService.findAll();
        for (News n : newsList) System.out.println(n);
//        News news = new News("complete short", "complete full", "complete title", new Date(), new Date());
//        Author author = new Author("John");
//        List<Tag> tags = new LinkedList<>();
//        tags.add(new Tag("first tag"));
//        tags.add(new Tag("second tag"));
//        newsService.addNews(news, author, tags);
        CommentService commentService = (CommentService) context.getBean("commentService");
        Comment comment = new Comment("testComment", new Date(), 104);
        commentService.addComment(comment);
    }
}
