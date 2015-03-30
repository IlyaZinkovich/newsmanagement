package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagDAO tagDAO;

    public int addTag(Tag tag) throws ServiceException {
        try {
            return tagDAO.insert(tag);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Integer> addTags(List<Tag> tags) throws ServiceException {
        try {
            return tagDAO.insert(tags);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Tag> findByNewsId(int newsId) {
        return tagDAO.findByNewsId(newsId);
    }
}
