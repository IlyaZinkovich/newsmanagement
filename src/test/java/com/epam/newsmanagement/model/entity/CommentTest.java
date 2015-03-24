package com.epam.newsmanagement.model.entity;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:comment-data.xml")
public class CommentTest {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private NewsDAO newsDAO;

    @Test
    public void addComment() throws DAOException {
        News news = newsDAO.findAll().get(0);
        Comment comment = new Comment("testCommetn", new Date(), news.getId());
        int commentId = commentDAO.insert(comment);
        Comment foundByIdComment = commentDAO.findById(commentId);
        assertThat(foundByIdComment, notNullValue());
        assertThat(foundByIdComment.getNewsId(), is(news.getId()));
    }

    @Test
    public void deleteComment() throws DAOException {
        Comment comment = commentDAO.findAll().get(0);
        int commentId = comment.getId();
        commentDAO.delete(comment);
        assertThat(commentDAO.findById(commentId), nullValue());
    }

}
