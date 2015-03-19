package com.epam.newsmanagement.controller;


import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.service.CommentService;
import com.epam.newsmanagement.service.NewsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
//        NewsService newsService = (NewsService) context.getBean("newsService");
//        List<News> newsList = newsService.findAll();
//        for (News n : newsList) System.out.println(n);
//        News newsmanagement = new News("complete short", "complete full", "complete title", new Date(), new Date());
//        Author author = new Author("John");
//        List<Tag> tags = new LinkedList<>();
//        tags.add(new Tag("first tag"));
//        tags.add(new Tag("second tag"));
//        newsService.addNews(newsmanagement, author, tags);
        CommentService commentService = (CommentService) context.getBean("commentService");
        Comment comment = new Comment("newComment", new Date(), 104);
        commentService.addComment(comment);
    }
}
