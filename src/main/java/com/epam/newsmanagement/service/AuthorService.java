package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    public int addAuthor(Author author) throws ServiceException {
        try {
            return authorDAO.insert(author);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Author findByNewsId(int newsId) {
        return authorDAO.findByNewsId(newsId);
    }
}
