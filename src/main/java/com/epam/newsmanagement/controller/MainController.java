package com.epam.newsmanagement.controller;


import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.service.NewsService;
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
//        NewsManagementService newsManagementService = (NewsManagementService) context.getBean("newsManagementService");
//        List<Tag> tags = new LinkedList<>();
//        tags.add(new Tag("second tag"));
//        tags.add(new Tag("first tag"));
//        tags.add(new Tag("fifth tag"));
//        newsManagementService.findNewsByTags(tags).forEach(System.out::println);
    }
}
