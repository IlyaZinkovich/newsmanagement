package com.epam.news.controller;


import com.epam.news.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.domain.News;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainController {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-module.xml");

        NewsDAO newsDAO = (NewsDAO) context.getBean("newsDAO");
        News news = new News();
        news.setId(1);
        news.setTitle("Title");
        news.setShortText("Short News");
        news.setFullText("Full News");
        newsDAO.insert(news);
        newsDAO.listAll().forEach(System.out :: println);
    }
}
