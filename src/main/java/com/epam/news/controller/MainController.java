package com.epam.news.controller;


import com.epam.news.model.domain.News;
import com.epam.news.model.persistence.interfaces.NewsDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class MainController {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");

        NewsDAO newsDAO = (NewsDAO) context.getBean("newsDAO");
        News news = new News();
        news.setId(1);
        news.setTitle("Title");
        news.setShortText("Short News");
        news.setFullText("Full News");
        news.setCreationDate(new Date());
        news.setModificationDate(new Date());
        newsDAO.insert(news);
        for (News n : newsDAO.findAll()) System.out.println(n);
    }
}
