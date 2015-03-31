package com.epam.newsmanagement.model.persistence.interfaces;


import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

import java.util.List;

public interface CommentDAO extends GenericDAO<Comment> {
    Comment findById(long id);
    void insert(List<Comment> comments) throws DAOException;
    List<Comment> findByNewsId(long newsId);
}
