package com.epam.newsmanagement.service.interfaces;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.service.exception.ServiceException;

import java.util.List;

public interface CommentService {
    long addComment(Comment comment) throws ServiceException;
    List<Comment> findByNewsId(long newsId);
    Comment findById(long commentId);
    void delete(Comment comment) throws ServiceException;
}
