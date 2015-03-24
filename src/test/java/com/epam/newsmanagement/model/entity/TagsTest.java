package com.epam.newsmanagement.model.entity;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
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
@DatabaseSetup("classpath:tags-data.xml")
public class TagsTest {

    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private TagDAO tagDAO;

    @Test
    public void addTags() throws DAOException {
        News news = newsDAO.findAll().get(0);
        List<Tag> initialTags = tagDAO.findByNewsId(news.getId());
        for (Tag t : initialTags) tagDAO.delete(t);
        List<Tag> tags = tagDAO.findAll();
        tagDAO.insert(tags, news.getId());
        List<Tag> foundTags = tagDAO.findByNewsId(news.getId());
        assertThat(foundTags.size(), is(tags.size()));
    }
}
