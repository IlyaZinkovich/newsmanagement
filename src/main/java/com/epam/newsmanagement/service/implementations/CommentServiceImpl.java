package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

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
    public List<Comment> findByNewsId(long newsId) {
        return commentDAO.findByNewsId(newsId);
    }

    @Override
    public Comment findById(long commentId) {
        return commentDAO.findById(commentId);
    }

    @Override
    public void delete(Comment comment) throws ServiceException {
        try {
            if (commentDAO.findById(comment.getId()) != null)
                commentDAO.delete(comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
