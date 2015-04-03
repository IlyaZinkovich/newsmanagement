package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.domain.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements com.epam.newsmanagement.service.interfaces.CommentService {

    private static Logger logger = Logger.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDAO commentDAO;

    public CommentServiceImpl() {
    }

    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public long addComment(Comment comment) throws ServiceException {
        try {
            return commentDAO.insert(comment);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Long> addComments(List<Comment> comments) throws ServiceException {
        try {
            return commentDAO.insert(comments);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void editComment(Comment comment) throws ServiceException {
        try {
            commentDAO.update(comment);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> findByNewsId(long newsId) throws ServiceException {
        try {
            return commentDAO.findByNewsId(newsId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Comment findById(long commentId) throws ServiceException {
        try {
            return commentDAO.findById(commentId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteComment(long commentId) throws ServiceException {
        try {
            commentDAO.delete(commentId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
