package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    public void addComment(Comment comment) {
        try {
            commentDAO.insert(comment);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(Comment comment) {
        try {
            commentDAO.delete(comment);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
