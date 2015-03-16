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
        NewsDAO newsDAO = (NewsDAO) context.getBean("oracleNewsDAO");
        News news = newsDAO.findByID(2);
        newsDAO.delete(news);
//        news.setId(1);
//        news.setTitle("TTitle");
//        news.setShortText("SShort News");
//        news.setFullText("FFull News");
//        news.setCreationDate(new Date());
//        news.setModificationDate(new Date());
//        newsDAO.insert(news);
        for (News n : newsDAO.findAll()) System.out.println(n);
    }
}
