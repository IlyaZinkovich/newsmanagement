package com.epam.news.service;

import com.epam.news.model.entity.Comment;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.interfaces.CommentDAO;
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
        commentDAO.delete(comment);
    }

}
