package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.implementations.NewsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsServiceTest {

    @Mock
    private NewsDAO newsDAO;

    private NewsServiceImpl newsService;

    private News testNews;
    private int testAuthorId;
    private int testTagId;
    private List<Integer> testTagsId;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsService = new NewsServiceImpl(newsDAO);
        testNews = new News(1, "short", "full", "title", new Date(), new Date());
        testAuthorId = 1;
        testTagId = 1;
        testTagsId = new LinkedList<>();
        testTagsId.add(1);
        testTagsId.add(2);
        testTagsId.add(3);
    }


    @Test
    public void editNewsSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        testNews.setTitle("testTitle");
        newsService.editNews(testNews);
        verify(newsDAO).findById(testNews.getId());
        ArgumentCaptor<News> newsCaptor = ArgumentCaptor.forClass(News.class);
        verify(newsDAO).update(newsCaptor.capture());
        News updatedNews = newsCaptor.getValue();
        assertEquals("testTitle", updatedNews.getTitle());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void editNewsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsService.editNews(testNews);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void deleteNewsSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        newsService.deleteNews(testNews.getId());
        verify(newsDAO).findById(testNews.getId());
        verify(newsDAO).delete(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void deleteNewsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsService.deleteNews(testNews.getId());
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

}
