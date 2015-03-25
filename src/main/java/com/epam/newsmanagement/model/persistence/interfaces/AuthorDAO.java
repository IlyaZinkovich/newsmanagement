package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.Date;

public interface AuthorDAO extends GenericDAO<Author> {
    Author findById(int author);
    Author findByNewsId(int newsId);
    Author findByName(String name);
    void update(int authorId, Date expirationDate);
}
