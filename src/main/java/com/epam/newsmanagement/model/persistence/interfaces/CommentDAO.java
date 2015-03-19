package com.epam.newsmanagement.model.persistence.interfaces;


import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface CommentDAO extends GenericDAO<Comment> {
    public Comment findById(int id);

}
