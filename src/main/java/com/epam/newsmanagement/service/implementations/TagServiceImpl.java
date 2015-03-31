package com.epam.newsmanagement.service.implementations;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDAO tagDAO;

    @Override
    public long addTag(Tag tag) throws ServiceException {
        try {
            return tagDAO.insert(tag);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Long> addTags(List<Tag> tags) throws ServiceException {
        try {
            return tagDAO.insert(tags);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tag> findByNewsId(long newsId) {
        return tagDAO.findByNewsId(newsId);
    }
}