package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    public void addNewsAuthor(Author author, int newsId) {
        try {
            authorDAO.insert(author, newsId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
