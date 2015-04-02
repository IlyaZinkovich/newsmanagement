package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.implementations.NewsManagementServiceImpl;
import com.epam.newsmanagement.service.interfaces.*;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsManagementServiceTest
{
    @Mock
    private NewsService newsService;
    @Mock
    private AuthorService authorService;
    @Mock
    private TagService tagService;
    @Mock
    private CommentService commentService;

    private NewsManagementServiceImpl newsManagementService;

    private News testNews;
    private Author testAuthor;
    private Tag testTag;
    private List<Tag> testTags;
    private Comment testComment;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        newsManagementService = new NewsManagementServiceImpl(newsService, authorService, tagService, commentService);
        testNews = new News(1, "short", "full", "title", new Date(), new Date());
        testAuthor = new Author(1, "author", null);
        testTag = new Tag(4, "tag");
        testTags = new LinkedList<>();
        testTags.add(new Tag(1, "1"));
        testTags.add(new Tag(2, "2"));
        testTags.add(new Tag(3, "3"));
        testComment = new Comment(1, "comment", new Date(), testNews.getId());
    }

    @Test
    public void editNewsSucceed() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        testNews.setTitle("testTitle");
        newsService.editNews(testNews);
        ArgumentCaptor<News> newsCaptor = ArgumentCaptor.forClass(News.class);
        verify(newsService).editNews(newsCaptor.capture());
        News updatedNews = newsCaptor.getValue();
        assertEquals("testTitle", updatedNews.getTitle());
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void editNewsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsService.editNews(testNews);
        verify(newsService).editNews(testNews);
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void deleteNewsSucceed() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsService.deleteNews(testNews.getId());
        verify(newsService).deleteNews(testNews.getId());
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void deleteNewsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsService.deleteNews(testNews.getId());
        verify(newsService).deleteNews(testNews.getId());
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void addNewsAuthorSucceed() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsAuthor(testNews.getId(), testAuthor);
        verify(newsService).findById(testNews.getId());
        verify(authorService).addAuthor(testAuthor);
        verify(newsService).addNewsAuthor(eq(testNews.getId()), anyLong());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    public void addNewsAuthorDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsAuthor(testNews.getId(), testAuthor);
        verify(newsService).findById(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    public void addNewsTagsSucceed() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsTags(testNews.getId(), testTags);
        verify(newsService).findById(testNews.getId());
        verify(tagService).addTags(testTags);
        verify(newsService).addNewsTags(eq(testNews.getId()), anyListOf(Long.class));
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addNewsTagsDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsTags(testNews.getId(), testTags);
        verify(newsService).findById(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addNewsTagSucceed() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsTag(testNews.getId(), testTag);
        verify(newsService).findById(testNews.getId());
        verify(tagService).addTag(testTag);
        verify(newsService).addNewsTag(eq(testNews.getId()), anyLong());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addNewsTagDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsTag(testNews.getId(), testTag);
        verify(newsService).findById(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

//    @Test
//    public void addCommentSucceed() throws ServiceException, DAOException {
//        when(newsService.findById(testNews.getId())).thenReturn(testNews);
//        commentService.addComments(testComment);
//        verify(commentService).addComments(testComment);
//        verifyNoMoreInteractions(newsService);
//        verifyNoMoreInteractions(commentService);
//    }
//
//    @Test
//    public void addCommentDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
//        when(newsService.findById(testNews.getId())).thenReturn(null);
//        commentService.addComments(testComment);
//        verifyNoMoreInteractions(newsService);
//        verifyNoMoreInteractions(commentService);
//    }
//
//    @Test
//    public void deleteCommentSucceed() throws ServiceException, DAOException {
//        when(commentService.findById(testComment.getId())).thenReturn(testComment);
//        commentService.deleteComment(testComment);
//        verify(commentService).deleteComment(testComment);
//        verifyNoMoreInteractions(commentService);
//    }
//
//    @Test
//    public void deleteCommentDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
//        when(commentService.findById(testComment.getId())).thenReturn(null);
//        commentService.deleteComment(testComment);
//        verifyNoMoreInteractions(commentService);
//    }

}