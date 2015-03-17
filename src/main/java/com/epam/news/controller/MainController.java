package com.epam.news.controller;


import com.epam.news.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.entity.News;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
        NewsDAO newsDAO = (NewsDAO) context.getBean("oracleNewsDAO");
        News news = newsDAO.findById(3);
        //if (news != null) newsDAO.delete(news);
        news.setTitle("updated");
        newsDAO.insert(news);
        for (News n : newsDAO.findAll()) System.out.println(n);
    }
}
