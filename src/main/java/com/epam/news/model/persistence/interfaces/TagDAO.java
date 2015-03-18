package com.epam.news.model.persistence.interfaces;


import com.epam.news.model.entity.Tag;
import com.epam.news.model.persistence.exception.DAOException;

public interface TagDAO {
    public void insert(Tag tag) throws DAOException;
    public void update(Tag tag);
    public void delete(Tag tag);
    public Tag findById(int id);
    public Tag findByName(String name);
}
