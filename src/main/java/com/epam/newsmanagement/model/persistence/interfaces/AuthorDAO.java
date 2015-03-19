package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface AuthorDAO extends GenericDAO<Author> {
    public Author findById(int author);
    public Author findByName(String name);
    public int insert(String authorName, int newsId) throws DAOException;
}
