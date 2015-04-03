package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.*;
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
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
    private List<Long> testTagsIdList;
    private List<Comment> testComments;

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
        testTagsIdList = testTags.stream().map(Tag::getId).collect(Collectors.toList());
        testComments = new LinkedList<>();
        testComments.add(new Comment(1, "first", new Date(), testNews.getId()));
        testComments.add(new Comment(2, "second", new Date(), testNews.getId()));
    }


    @Test
    public void addNewsAuthorSucceed() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsAuthor(testNews.getId(), testAuthor);
        verify(newsService).findById(testNews.getId());
        verify(authorService).addAuthor(testAuthor);
        verify(newsService).addNewsAuthor(eq(testNews.getId()), anyLong());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    public void addNewsAuthorDoesNothingIfNewsNotFound() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsAuthor(testNews.getId(), testAuthor);
        verify(newsService).findById(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    public void addNewsTagsSucceed() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsTags(testNews.getId(), testTags);
        verify(newsService).findById(testNews.getId());
        verify(tagService).addTags(testTags);
        verify(newsService).addNewsTags(eq(testNews.getId()), anyListOf(Long.class));
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addNewsTagsDoesNothingIfNewsNotFound() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsTags(testNews.getId(), testTags);
        verify(newsService).findById(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addNewsTagSucceed() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        newsManagementService.addNewsTag(testNews.getId(), testTag);
        verify(newsService).findById(testNews.getId());
        verify(tagService).addTag(testTag);
        verify(newsService).addNewsTag(eq(testNews.getId()), anyLong());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addNewsTagDoesNothingIfNewsNotFound() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(null);
        newsManagementService.addNewsTag(testNews.getId(), testTag);
        verify(newsService).findById(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void addComplexNewsSucceed() throws Exception {
        when(newsService.addNews(testNews)).thenReturn(testNews.getId());
        when(authorService.addAuthor(testAuthor)).thenReturn(testAuthor.getId());
        when(tagService.addTags(testTags)).thenReturn(testTagsIdList);
        newsManagementService.addComplexNews(testNews, testAuthor, testTags);
        verify(newsService).addNews(testNews);
        ArgumentCaptor<Long> newsIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(authorService).addAuthor(testAuthor);
        verify(newsService).addNewsAuthor(newsIdCaptor.capture(), eq(testAuthor.getId()));
        assertThat(testNews.getId(), is(newsIdCaptor.getValue()));
        verify(tagService).addTags(testTags);
        verify(newsService).addNewsTags(newsIdCaptor.capture(), eq(testTagsIdList));
        assertThat(testNews.getId(), is(newsIdCaptor.getValue()));
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(authorService);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void findComplexNewsByIdSucceed() throws Exception {
        when(newsService.findById(testNews.getId())).thenReturn(testNews);
        when(authorService.findByNewsId(testNews.getId())).thenReturn(testAuthor);
        when(tagService.findByNewsId(testNews.getId())).thenReturn(testTags);
        when(commentService.findByNewsId(testNews.getId())).thenReturn(testComments);
        ComplexNews complexNews = newsManagementService.findComplexNewsById(testNews.getId());
        assertThat(complexNews.getNews(), is(testNews));
        assertThat(complexNews.getAuthor(), is(testAuthor));
        assertThat(complexNews.getTags(), is(testTags));
        assertThat(complexNews.getComments(), is(testComments));
        verify(newsService).findById(testNews.getId());
        verify(authorService).findByNewsId(testNews.getId());
        verify(tagService).findByNewsId(testNews.getId());
        verify(commentService).findByNewsId(testNews.getId());
        verifyNoMoreInteractions(newsService);
        verifyNoMoreInteractions(authorService);
        verifyNoMoreInteractions(tagService);
        verifyNoMoreInteractions(commentService);
    }

}