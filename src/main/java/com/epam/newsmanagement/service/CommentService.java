package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    public CommentService() {
    }

    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;

    }

    public int addComment(Comment comment) throws ServiceException {
        try {
            return commentDAO.insert(comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Comment> findByNewsId(int newsId) {
        return commentDAO.findByNewsId(newsId);
    }

    public Comment findById(int commentId) {
        return commentDAO.findById(commentId);
    }

    public void delete(Comment comment) throws ServiceException {
        try {
            if (commentDAO.findById(comment.getId()) != null)
                commentDAO.delete(comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
