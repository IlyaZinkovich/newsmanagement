package com.epam.newsmanagement.model.persistence.interfaces;


import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface TagDAO extends GenericDAO<Tag> {
    List<Integer> insert(List<Tag> tags) throws DAOException;
    Tag findById(int id);
    List<Tag> findByNewsId(int newsId);
    Tag findByName(String name);
}
