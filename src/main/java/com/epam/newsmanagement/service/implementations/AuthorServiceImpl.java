package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    public long addAuthor(Author author) throws ServiceException {
        try {
            return authorDAO.insert(author);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author findByNewsId(long newsId) {
        return authorDAO.findByNewsId(newsId);
    }

}
