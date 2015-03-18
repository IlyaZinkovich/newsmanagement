package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.NewsTag;
import com.epam.news.model.entity.Tag;
import com.epam.news.model.persistence.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface NewsTagDAO {
    public void insert(NewsTag newsTag) throws DAOException;
    public void insert(String tagName, int newsId) throws DAOException;
    public void insert(Map<String, Integer> tagNameNewsIdMap) throws DAOException;
    public void insert(List<Tag> tags, int newsId) throws DAOException;
    public void insert(List<NewsTag> newsTagList) throws DAOException;
}
