package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.domain.News;
import com.epam.newsmanagement.model.domain.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:dbunitxml/news-data.xml")
public class NewsDAOTest {

    @Autowired
    private NewsDAO newsDAO;

    private News testNews;
    private final int testDataSize = 3;
    private final String testAuthorName = "John";
    private final String testTagName = "first";
    private List<Tag> testTagList;

    @Before
    public void setUp() {
        testNews = new News(4, "fourthShort", "fourthFull", "fourthTitle", new Date(), new Date());
        testTagList = new LinkedList<>();
        testTagList.add(new Tag(1, "first"));
        testTagList.add(new Tag(2, "second"));
    }

    @Test
    public void findAllSucceed() throws Exception {
        List<News> foundNews = newsDAO.findAll();
        assertThat(foundNews, notNullValue());
        assertEquals(foundNews.size(), testDataSize);
    }

    @Test
    public void addNewsSucceed() throws Exception {
        int beforeSize = newsDAO.findAll().size();
        Long newsId = newsDAO.insert(testNews);
        int afterSize = newsDAO.findAll().size();
        assertThat(afterSize, is(beforeSize + 1));
        assertThat(newsDAO.findById(newsId), notNullValue());
    }

    @Test
    public void editNewsSucceed() throws Exception {
        long newsToUpdateId = newsDAO.findAll().get(0).getId();
        testNews.setId(newsToUpdateId);
        newsDAO.update(testNews);
        News news = newsDAO.findById(testNews.getId());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertThat(news.getShortText(), is(testNews.getShortText()));
        assertThat(news.getFullText(), is(testNews.getFullText()));
        assertThat(news.getTitle(), is(testNews.getTitle()));
        assertThat(news.getCreationDate(), is(testNews.getCreationDate()));
        assertThat(formatter.format(news.getModificationDate()), is(formatter.format(testNews.getModificationDate())));
    }


    @Test
    public void deleteNewsSucceed() throws DAOException {
        List<News> newsList = newsDAO.findAll();
        int beforeSize = newsList.size();
        long newsToDeleteId = newsList.get(0).getId();
        newsDAO.delete(newsToDeleteId);
        int afterSize = newsDAO.findAll().size();
        assertThat(afterSize, is(beforeSize - 1));
        assertThat(newsDAO.findById(newsToDeleteId), nullValue());
    }

    @Test
    public void findSingleNewsSucceed() throws Exception {
        Long newsId = newsDAO.findAll().get(0).getId();
        News news = newsDAO.findById(newsId);
        assertThat(news, notNullValue());
        assertThat(news.getId(), is(newsId));
    }

    @Test
    public void findNewsByTagSucceed() throws Exception {
        List<News> foundNews = newsDAO.findByTag(testTagName);
        assertEquals(foundNews.size(), 2);
    }

    @Test
    public void findNewsByAuthorSucceed() throws Exception {
        List<News> foundNews = newsDAO.findByAuthor(testAuthorName);
        assertEquals(foundNews.size(), 2);
    }

    @Test
    public void findNewsByTagsSucceed() throws Exception {
        List<News> foundNews = newsDAO.findByTags(testTagList);
        assertEquals(foundNews.size(), 2);
    }
}
