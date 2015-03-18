package com.epam.news.service;

import com.epam.news.model.entity.NewsAuthor;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.interfaces.NewsAuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsAuthorService {

    @Autowired
    private NewsAuthorDAO newsAuthorDAO;

    public void addNewsAuthor(NewsAuthor newsAuthor) {
        try {
            newsAuthorDAO.insert(newsAuthor);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void addNewsAuthor(String authorName, int newsId) {
        try {
            newsAuthorDAO.insert(authorName, newsId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
