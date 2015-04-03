package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.implementations.NewsServiceImpl;
import com.epam.newsmanagement.service.interfaces.NewsService;
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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsServiceTest {

    @Mock
    private NewsDAO newsDAO;

    private NewsService newsService;

    private News testNews;
    private int testTagId;
    private Author testAuthor;
    private List<Long> testTagsId;
    private List<Tag> testTags;
    private List<News> testNewsList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsService = new NewsServiceImpl(newsDAO);
        testNews = new News(1, "short", "full", "title", new Date(), new Date());
        testAuthor = new Author(1, "author", null);
        testTagId = 1;
        testTagsId = new LinkedList<>();
        testTagsId.add(1l);
        testTagsId.add(2l);
        testTagsId.add(3l);
        testTags = new LinkedList<>();
        testTags.add(new Tag(1, "first"));
        testTags.add(new Tag(2, "second"));
        testTags.add(new Tag(3, "third"));
        testNewsList = new LinkedList<>();
        testNewsList.add(testNews);
    }

    @Test
    public void addNewsSucceed() throws Exception {
        when(newsDAO.insert(testNews)).thenReturn(1l);
        long generatedId = newsService.addNews(testNews);
        assertThat(generatedId, greaterThan(0l));
        verify(newsDAO).insert(testNews);
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void editNewsSucceed() throws Exception {
        testNews.setTitle("testTitle");
        newsService.editNews(testNews);
        ArgumentCaptor<News> newsCaptor = ArgumentCaptor.forClass(News.class);
        verify(newsDAO).update(newsCaptor.capture());
        News updatedNews = newsCaptor.getValue();
        assertThat(updatedNews, is(testNews));
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void deleteNewsSucceed() throws Exception {
        newsService.deleteNews(testNews.getId());
        verify(newsDAO).delete(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void findByIdSucceed() throws Exception {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        News foundNews = newsService.findById(testNews.getId());
        assertThat(foundNews, is(testNews));
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void addNewsAuthorSucceed() throws Exception {
        newsService.addNewsAuthor(testNews.getId(), testAuthor.getId());
        verify(newsDAO).insertNewsAuthor(testNews.getId(), testAuthor.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void addNewsTagSucceed() throws Exception {
        newsService.addNewsTag(testNews.getId(), testTagId);
        verify(newsDAO).insertNewsTag(testNews.getId(), testTagId);
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void addNewsTagsSucceed() throws Exception {
        newsService.addNewsTags(testNews.getId(), testTagsId);
        verify(newsDAO).insertNewsTags(testNews.getId(), testTagsId);
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void findNewsByTagsSucceed() throws Exception {
        when(newsDAO.findByTags(testTags)).thenReturn(testNewsList);
        List<News> foundNews = newsService.findByTags(testTags);
        assertThat(foundNews, is(testNewsList));
        verify(newsDAO).findByTags(testTags);
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void findAllNewsSucceed() throws Exception {
        when(newsDAO.findAll()).thenReturn(testNewsList);
        List<News> foundNews = newsService.findAll();
        assertThat(foundNews, is(testNewsList));
        verify(newsDAO).findAll();
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void findNewsByAuthorSucceed() throws Exception {
        when(newsDAO.findByAuthor(testAuthor.getName())).thenReturn(testNewsList);
        List<News> foundNews = newsService.findByAuthor(testAuthor.getName());
        assertThat(foundNews, is(testNewsList));
        verify(newsDAO).findByAuthor(testAuthor.getName());
        verifyNoMoreInteractions(newsDAO);
    }
}
