package com.epam.newsmanagement.model.persistence.interfaces;


import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface TagDAO extends GenericDAO<Tag> {
    public Tag findById(int id);
    public Tag findByName(String name);
    public void insert(List<String> tags, int newsId) throws DAOException;
}
