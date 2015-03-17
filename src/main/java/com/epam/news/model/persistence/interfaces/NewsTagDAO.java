package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.NewsTag;

import java.util.List;
import java.util.Map;

public interface NewsTagDAO {
    public void insert(NewsTag newsTag);
    public void insertNewsTagWithTagNameNewsId(String tagName, int newsId);
    public void insertNewsTagWithTagNameNewsId(Map<String, Integer> tagNameNewsIdMap);
    public void insert(List<NewsTag> newsTagList);
}
