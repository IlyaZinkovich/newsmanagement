package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.domain.Comment;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:dbunitxml/comment-data.xml")
public class CommentDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    private Comment testComment;
    private Comment existingTestComment;
    private List<Comment> testCommentsList;
    private final int testDataSize = 3;

    @Before
    public void setUp() {
        testComment = new Comment(4, "fourth", new Date(), 3);
        existingTestComment = new Comment(1, "first", new Date(), 1);
        testCommentsList = new LinkedList<>();
        testCommentsList.add(testComment);
        testCommentsList.add(existingTestComment);
    }

    @Test
    public void findAllSucceed() throws Exception {
        List<Comment> foundComments = commentDAO.findAll();
        assertThat(foundComments.size(), is(testDataSize));
    }

    @Test
    public void insertListSucceed() throws Exception {
        int size = commentDAO.findAll().size();
        commentDAO.insert(testCommentsList);
        assertEquals(commentDAO.findAll().size(), size + testCommentsList.size());
    }

    @Test
    public void insertCommentSucceed() throws Exception {
        List<Comment> commentsBefore = commentDAO.findAll();
        long generatedId = commentDAO.insert(testComment);
        assertThat(generatedId, greaterThan(0l));
        testComment.setId(generatedId);
        List<Comment> commentsAfter = commentDAO.findAll();
        assertThat(commentsAfter.size(), is(commentsBefore.size() + 1));
        assertTrue(commentsAfter.contains(testComment));
    }

    @Test
    public void updateCommentSucceed() throws Exception {
        List<Comment> foundCommentsBefore = commentDAO.findAll();
        Comment commentToUpdate = foundCommentsBefore.get(0);
        commentToUpdate.setCreationDate(new Date());
        commentToUpdate.setCommentText(testComment.getCommentText());
        commentToUpdate.setNewsId(testComment.getNewsId());
        commentDAO.update(commentToUpdate);
        Comment updatedComment = commentDAO.findById(commentToUpdate.getId());
        assertEquals(commentToUpdate, updatedComment);
    }

    @Test
    public void deleteCommentSucceed() throws Exception {
        List<Comment> foundCommentsBefore = commentDAO.findAll();
        Comment commentToDelete = foundCommentsBefore.get(0);
        commentDAO.delete(commentToDelete.getId());
        Comment deletedComment = commentDAO.findById(commentToDelete.getId());
        assertThat(deletedComment, nullValue());
    }

    @Test
    public void findByNewsIdSucceed() throws Exception {
        List<Comment> foundComments = commentDAO.findByNewsId(1);
        assertEquals(foundComments.size(), 2);
    }

    @Test
    public void findByNewsIdReturnsNullIfNewsDoesNotHaveComments() throws Exception {
        List<Comment> foundComments = commentDAO.findByNewsId(5);
        assertThat(eq(foundComments), nullValue());
    }

}
