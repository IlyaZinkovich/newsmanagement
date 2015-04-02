package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.service.implementations.CommentServiceImpl;
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
public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAO;

    private CommentService commentService;

    private Comment testComment;
    private List<Comment> testComments;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentDAO);
        testComment = new Comment(1, "comment", new Date(), 1);
        testComments = new LinkedList<>();
        testComments.add(testComment);
        testComments.add(new Comment(2, "first", new Date(), 1));
    }

    @Test
    public void addCommentsSucceed() throws Exception {
        commentService.addComments(testComments);
        verify(commentDAO, times(testComments.size())).insert(any(Comment.class));
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void editCommentSucceed() throws Exception {
        commentService.editComment(testComment);
        verify(commentDAO).update(testComment);
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void deleteCommentSucceed() throws Exception {
        commentService.deleteComment(testComment.getId());
        verify(commentDAO).delete(testComment.getId());
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void findByNewsIdSucceed() throws Exception {
        when(commentDAO.findByNewsId(testComment.getNewsId())).thenReturn(testComments);
        commentService.findByNewsId(testComment.getNewsId());
        verify(commentDAO).findByNewsId(testComment.getNewsId());
        verifyNoMoreInteractions(commentDAO);
    }

    @Test
    public void findByIdSucceed() throws Exception {
        commentService.findById(testComment.getId());
        verify(commentDAO).findById(testComment.getId());
        verifyNoMoreInteractions(commentDAO);
    }

}
