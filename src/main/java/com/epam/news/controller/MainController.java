package com.epam.news.controller;


import com.epam.news.model.entity.Comment;
import com.epam.news.model.entity.News;
import com.epam.news.service.CommentService;
import com.epam.news.service.NewsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
        NewsService newsService = (NewsService) context.getBean("newsService");
        List<News> newsList = newsService.findAll();
        for (News n : newsList) System.out.println(n);
    }
}
