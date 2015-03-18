package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.NewsAuthor;
import com.epam.news.model.persistence.exception.DAOException;

/**
 * Created by Ilya_Zinkovich on 3/17/2015.
 */
public interface NewsAuthorDAO {
    public void insert(NewsAuthor newsAuthor) throws DAOException;
    public void insert(String authorName, int newsId) throws DAOException;
}
