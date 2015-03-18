package com.epam.news.service;

import com.epam.news.model.entity.NewsTag;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.interfaces.NewsTagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsTagService {

    @Autowired
    private NewsTagDAO newsTagDAO;

    public void addNewsTag(NewsTag newsTag) {
        try {
            newsTagDAO.insert(newsTag);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void addNewsTag(String tagName, int newsId) {
        try {
            newsTagDAO.insert(tagName, newsId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
