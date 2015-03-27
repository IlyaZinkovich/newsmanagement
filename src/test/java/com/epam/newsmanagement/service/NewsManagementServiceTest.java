package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.epam.newsmanagement.service.exception.NewsNotFoundException;
import com.epam.newsmanagement.service.exception.ServiceException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsManagementServiceTest
{
    @Mock
    private NewsDAO newsDAO;
    @Mock
    private AuthorDAO authorDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private CommentDAO commentDAO;

    private NewsManagementService newsManagementService;

    private News testNews;
    private Author testAuthor;
    private Tag testTag;
    private List<Tag> testTags;
    private Comment testComment;
    private ComplexNews testComplexNews;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsManagementService = new NewsManagementService(newsDAO, authorDAO, tagDAO, commentDAO);
        testNews = new News(1, "short", "full", "title", new Date(), new Date());
        testAuthor = new Author("author");
        testTag = new Tag("tag");
        testTags = new LinkedList<>();
        testTags.add(new Tag("1"));
        testTags.add(new Tag("2"));
        testTags.add(new Tag("3"));
        testComment = new Comment("comment", new Date(), testNews.getId());
        testComplexNews = new ComplexNews(testNews, testAuthor, testTags);
    }

    @Test
    public void editNewsSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        testNews.setTitle("testTitle");
        newsManagementService.editNews(testNews);
        verify(newsDAO).findById(1);
        ArgumentCaptor<News> newsCaptor = ArgumentCaptor.forClass(News.class);
        verify(newsDAO).update(newsCaptor.capture());
        News updatedNews = newsCaptor.getValue();
        assertEquals("testTitle", updatedNews.getTitle());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void editNewsDoesNothingIfNewsNotFound() throws ServiceException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsManagementService.editNews(testNews);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void deleteNewsSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.deleteNews(testNews);
        verify(newsDAO).findById(testNews.getId());
        verify(newsDAO).delete(testNews);
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void deleteNewsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsManagementService.deleteNews(testNews);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void addNewsAuthorSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsAuthor(testNews.getId(), testAuthor);
        verify(newsDAO).findById(testNews.getId());
        verify(authorDAO).insert(testAuthor);
        verify(newsDAO).insertNewsAuthor(eq(testNews.getId()), anyInt());
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(authorDAO);
    }

    @Test
    public void addNewsAuthorDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsAuthor(testNews.getId(), testAuthor);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(authorDAO);
    }

    @Test
    public void addNewsTagsSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsTags(testNews.getId(), testTags);
        verify(newsDAO).findById(testNews.getId());
        verify(tagDAO).insert(testTags);
        verify(newsDAO).insertNewsTags(eq(testNews.getId()), anyListOf(Integer.class));
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void addNewsTagsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsTags(testNews.getId(), testTags);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void addNewsTagSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsTag(testNews.getId(), testTag);
        verify(newsDAO).findById(testNews.getId());
        verify(tagDAO).insert(testTag);
        verify(newsDAO).insertNewsTag(eq(testNews.getId()), any(Integer.class));
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void addNewsTagDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsTag(testNews.getId(), testTag);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void addCommentSucceed() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addComment(testComment);
        verify(newsDAO).findById(testNews.getId());
        verify(commentDAO).insert(testComment);
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void addCommentDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsDAO.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addComment(testComment);
        verify(newsDAO).findById(testNews.getId());
        verifyNoMoreInteractions(newsDAO);
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void deleteCommentSucceed() throws ServiceException, DAOException {
        when(commentDAO.findById(testComment.getId())).thenReturn(testComment);
        newsManagementService.deleteComment(testComment);
        verify(commentDAO).findById(testComment.getId());
        verify(commentDAO).delete(testComment);
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void deleteCommentDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(commentDAO.findById(testComment.getId())).thenReturn(null);
        newsManagementService.deleteComment(testComment);
        verify(commentDAO).findById(testComment.getId());
        verifyNoMoreInteractions(commentDAO);
    }

}