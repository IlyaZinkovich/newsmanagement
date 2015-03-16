package com.epam.news.model.persistence.interfaces;


import com.epam.news.model.entity.Tag;

public interface TagDAO {
    public void insert(Tag tag);
    public void update(Tag tag);
    public void delete(Tag tag);
    public Tag findByID(int id);
}
