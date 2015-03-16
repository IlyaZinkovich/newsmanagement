package com.epam.news.model.entity.service;

import com.epam.news.model.entity.model.entity.News;
import com.epam.news.model.entity.model.persistence.interfaces.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public List<News> findAll() {
        return newsDAO.findAll();
    }

}
