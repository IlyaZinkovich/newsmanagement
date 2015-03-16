package com.epam.news.model.persistence.interfaces;


import com.epam.news.model.entity.Comment;

public interface CommentsDAO {
    public void insert(Comment comment);
    public void update(Comment comment);
    public void delete(Comment comment);
    public Comment findByID(int id);
}
