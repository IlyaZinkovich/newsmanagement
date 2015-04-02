package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CommentServiceImpl implements com.epam.newsmanagement.service.interfaces.CommentService {

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
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Long> addComments(List<Comment> comments) throws ServiceException {
        List<Long> commentsIdList = new LinkedList<>();
        try {
            for (Comment comment : comments) {
                long commentId = commentDAO.insert(comment);
                commentsIdList.add(commentId);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return commentsIdList;
    }

    @Override
    public void editComment(Comment comment) throws ServiceException {
        try {
            commentDAO.update(comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Comment> findByNewsId(long newsId) throws ServiceException {
        try {
            return commentDAO.findByNewsId(newsId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Comment findById(long commentId) throws ServiceException {
        try {
            return commentDAO.findById(commentId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteComment(long commentId) throws ServiceException {
        try {
            commentDAO.delete(commentId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
