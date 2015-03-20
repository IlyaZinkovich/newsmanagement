package com.epam.newsmanagement.model.persistence.interfaces;


import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface TagDAO extends GenericDAO<Tag> {
    Tag findById(int id);
    List<Tag> findByNewsId(int newsId);
    Tag findByName(String name);
    void insert(List<Tag> tags, int newsId) throws DAOException;
}
