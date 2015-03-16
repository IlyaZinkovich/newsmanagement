package com.epam.news.service;

import com.epam.news.model.persistence.interfaces.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

}
