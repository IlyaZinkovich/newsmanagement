package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.exception.ServiceException;
import com.epam.newsmanagement.service.implementations.CommentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAO;

    private CommentServiceImpl commentService;

    private Comment testComment;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentDAO);
        testComment = new Comment("comment", new Date(), 1);
    }


    @Test
    public void deleteCommentSucceed() throws ServiceException, DAOException {
        when(commentDAO.findById(testComment.getId())).thenReturn(testComment);
        commentService.delete(testComment);
        verify(commentDAO).findById(testComment.getId());
        verify(commentDAO).delete(testComment);
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void deleteCommentDoesNothingIfNewsNotFound() throws ServiceException, DAOException {
        when(commentDAO.findById(testComment.getId())).thenReturn(null);
        commentService.delete(testComment);
        verify(commentDAO).findById(testComment.getId());
        verifyNoMoreInteractions(commentDAO);
    }


}
