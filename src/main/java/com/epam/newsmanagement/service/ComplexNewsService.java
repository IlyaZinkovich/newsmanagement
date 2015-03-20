package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.entity.ComplexNews;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.ComplexNewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComplexNewsService {

    @Autowired
    private ComplexNewsDAO complexNewsDAO;

    @Transactional
    public void addComplexNews(News news, Author author, List<Tag> tags) {
        ComplexNews complexNews = new ComplexNews(news, author, tags);
        try {
            complexNewsDAO.insert(complexNews);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
