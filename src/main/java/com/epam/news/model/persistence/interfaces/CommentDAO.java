package com.epam.news.model.persistence.interfaces;


import com.epam.news.model.entity.Comment;
import com.epam.news.model.persistence.exception.DAOException;

public interface CommentDAO {
    public void insert(Comment comment) throws DAOException;
    public void update(Comment comment);
    public void delete(Comment comment);
    public Comment findById(int id);
}
