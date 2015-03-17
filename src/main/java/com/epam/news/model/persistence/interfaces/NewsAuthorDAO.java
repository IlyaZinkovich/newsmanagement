package com.epam.news.model.persistence.interfaces;

import com.epam.news.model.entity.NewsAuthor;

/**
 * Created by Ilya_Zinkovich on 3/17/2015.
 */
public interface NewsAuthorDAO {
    public void insert(NewsAuthor newsAuthor);
    public void insertNewsAuthorWithAuthorNameNewsId(String authorName, int newsId);
}
