package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.Author;

public interface AuthorDAO {
    public void insert(Author author);
    public void update(Author author);
    public void delete(Author author);
    public Author findById(int author);
    public Author findByName(String name);
}
