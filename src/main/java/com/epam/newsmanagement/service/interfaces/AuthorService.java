package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.service.exception.ServiceException;

public interface AuthorService {
    long addAuthor(Author author) throws ServiceException;
    Author findByNewsId(long newsId);
}
