package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.epam.newsmanagement.service.exception.NewsDoesNotExistException;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsServiceTest
{
    @Mock
    private NewsDAO newsDAO;
    @Mock
    private AuthorDAO authorDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private CommentDAO commentDAO;

    private NewsService newsService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsService = new NewsService(newsDAO, authorDAO, tagDAO, commentDAO);
    }

    @Test
    public void shouldUpdateNews() throws ServiceException, DAOException {
        News news = new News(1, "short", "full", "title", new Date(), new Date());
        when(newsDAO.findById(1)).thenReturn(news);
        news.setTitle("testTitle");
        newsService.editNews(news);
        verify(newsDAO).findById(1);
        ArgumentCaptor<News> newsCaptor = ArgumentCaptor.forClass(News.class);
        verify(newsDAO).update(newsCaptor.capture());
        News updatedNews = newsCaptor.getValue();
        assertEquals("testTitle", updatedNews.getTitle());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test(expected = NewsDoesNotExistException.class)
    public void shouldNotUpdateIfNewsNotFound() throws ServiceException {
        when(newsDAO.findById(1)).thenReturn(null);
        News news = new News(1, "short", "full", "title", new Date(), new Date());
        newsService.editNews(news);
    }

}