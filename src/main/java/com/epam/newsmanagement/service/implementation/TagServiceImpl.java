package com.epam.newsmanagement.service.implementation;

import com.epam.newsmanagement.domain.Tag;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.TagDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static Logger logger = Logger.getLogger(TagServiceImpl.class);

    @Autowired
    private TagDAO tagDAO;

    public TagServiceImpl() {
    }

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public long addTag(Tag tag) throws ServiceException {
        try {
            return tagDAO.insert(tag);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Long> addTags(List<Tag> tags) throws ServiceException {
        try {
            return tagDAO.insert(tags);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void editTag(Tag tag) throws ServiceException {
        try {
            tagDAO.update(tag);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteTag(long tagId) throws ServiceException {
        try {
            tagDAO.delete(tagId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tag> findByNewsId(long newsId) throws ServiceException {
        try {
            return tagDAO.findByNewsId(newsId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
