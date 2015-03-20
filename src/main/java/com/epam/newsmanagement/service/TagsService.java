package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    @Autowired
    private TagDAO tagDAO;

    public void addNewsTags(List<Tag> tags, int newsId) {
        try {
            tagDAO.insert(tags, newsId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
