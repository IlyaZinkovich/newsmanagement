package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.implementations.AuthorServiceImpl;
import com.epam.newsmanagement.service.implementations.CommentServiceImpl;
import com.epam.newsmanagement.service.interfaces.AuthorService;
import com.epam.newsmanagement.service.interfaces.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorDAO authorDAO;

    private AuthorService authorService;

    private Author testAuthor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authorService = new AuthorServiceImpl(authorDAO);
        testAuthor = new Author(1, "John", null);
    }

    @Test
    public void addAuthorSucceed() throws Exception {
        authorService.addAuthor(testAuthor);
        verify(authorDAO).insert(testAuthor);
        verifyNoMoreInteractions(authorDAO);
    }

    @Test
    public void editAuthorSucceed() throws Exception {
        authorService.editAuthor(testAuthor);
        verify(authorDAO).update(testAuthor);
        verifyNoMoreInteractions(authorDAO);
    }

    @Test
    public void deleteAuthorSucceed() throws Exception {
        authorService.deleteAuthor(testAuthor.getId());
        verify(authorDAO).delete(testAuthor.getId());
        verifyNoMoreInteractions(authorDAO);
    }

    @Test
    public void findByNewsIdSucceed() throws Exception {
        when(authorDAO.findByNewsId(1)).thenReturn(testAuthor);
        authorService.findByNewsId(1);
        verify(authorDAO).findByNewsId(1);
        verifyNoMoreInteractions(authorDAO);
    }

    @Test
    public void findByIdSucceed() throws Exception {
        when(authorDAO.findById(testAuthor.getId())).thenReturn(testAuthor);
        authorService.findById(testAuthor.getId());
        verify(authorDAO).findById(testAuthor.getId());
        verifyNoMoreInteractions(authorDAO);
    }
}
