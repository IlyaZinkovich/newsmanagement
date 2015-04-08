package com.epam.newsmanagement.service.implementation;

import com.epam.newsmanagement.domain.Author;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.AuthorDAO;
import com.epam.newsmanagement.service.AuthorService;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static Logger logger = Logger.getLogger(AuthorServiceImpl.class);

    @Autowired
    private AuthorDAO authorDAO;

    public AuthorServiceImpl() {
    }

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public long addAuthor(Author author) throws ServiceException {
        try {
            return authorDAO.insert(author);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void editAuthor(Author author) throws ServiceException {
        try {
            authorDAO.update(author);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteAuthor(long authorId) throws ServiceException {
        try {
            authorDAO.delete(authorId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Author findByNewsId(long newsId) throws ServiceException {
        try {
            return authorDAO.findByNewsId(newsId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Author findById(long authorId) throws ServiceException {
        try {
            return authorDAO.findById(authorId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

}
