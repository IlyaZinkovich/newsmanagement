package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface AuthorDAO extends GenericDAO<Author> {
    Author findById(int author);
    Author findByNewsId(int newsId);
    Author findByName(String name);
    int insert(Author author, int newsId) throws DAOException;
}
