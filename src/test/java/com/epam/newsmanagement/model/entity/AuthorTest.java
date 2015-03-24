package com.epam.newsmanagement.model.entity;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.model.persistence.interfaces.UserDAO;
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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:author-data.xml")
public class AuthorTest {

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private NewsDAO newsDAO;

    @Test
    public void addAuthor() throws DAOException {
        Author author = new Author("testAuthorName");
        News news = new News("testShort", "testFull", "testTitle", new Date(), new Date());
        int authorId = authorDAO.insert(author, news);
        assertThat(authorDAO.findById(authorId), notNullValue());
        assertThat(newsDAO.findByAuthor(authorId), notNullValue());
    }

}
