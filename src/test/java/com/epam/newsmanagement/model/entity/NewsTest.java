package com.epam.newsmanagement.model.entity;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hamcrest.Matchers;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:news-data.xml")
public class NewsTest {

    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private TagDAO tagDAO;

    @Test
    public void addNews() throws DAOException {
        News news = new News("short", "full", "title", new Date(), new Date());
        int newsId = newsDAO.insert(news);
        int count = newsDAO.findAll().size();
        assertThat(count, is(4));
    }

    @Test
    public void editNews() throws DAOException {
        News news = newsDAO.findAll().get(0);
        int newsId = news.getId();
        Date testDate = new Date();
        String testShort = "testShort";
        String testFull = "testFull";
        String testTitle = "testTitle";
        setNewsTestData(news, testDate, testShort, testFull, testTitle);
        newsDAO.update(news);
        news = newsDAO.findById(newsId);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertThat(news.getShortText(), is(testShort));
        assertThat(news.getFullText(), is(testFull));
        assertThat(news.getTitle(), is(testTitle));
        assertThat(news.getCreationDate(), is(testDate));
        assertThat(formatter.format(news.getModificationDate()), is(formatter.format(testDate)));
    }

    private void setNewsTestData(News news, Date testDate, String testShort, String testFull, String testTitle) {
        news.setShortText(testShort);
        news.setFullText(testFull);
        news.setTitle(testTitle);
        news.setCreationDate(testDate);
        news.setModificationDate(testDate);
    }

    @Test
    public void deleteNews() throws DAOException {
        List<News> newsList = newsDAO.findAll();
        News news = newsList.get(0);
        int newsToDeleteId = news.getId();
        newsDAO.delete(news);
        int newsCount = newsDAO.findAll().size();
        int actualCount = newsList.size() - 1;
        assertThat(newsCount, is(actualCount));
        assertThat(newsDAO.findById(newsToDeleteId), nullValue());
    }

    @Test
    public void findAllNews() {
        List<News> newsList = newsDAO.findAll();
        int maxCommentsCount = newsList.stream().mapToInt(n -> commentDAO.findByNewsId(n.getId()).size()).max().getAsInt();
        int firstNewsCommentsCount = commentDAO.findByNewsId(newsList.get(0).getId()).size();
        assertThat(newsList.size(), is(3));
        assertThat(firstNewsCommentsCount, is(maxCommentsCount));
    }

    @Test
    public void findSingleNews() {
        int newsId = newsDAO.findAll().get(0).getId();
        News news = newsDAO.findById(newsId);
        assertThat(news, notNullValue());
        assertThat(news.getId(), is(newsId));
    }

    @Test
    public void findByTags() throws DAOException {
        News news = newsDAO.findAll().get(0);
        List<Tag> tags = tagDAO.findAll();
        newsDAO.insertNewsTags(news.getId(), tags.stream().mapToInt(Tag::getId).boxed().collect(Collectors.toList()));
        List<News> foundByTagsNews = newsDAO.findByTags(tags);
        assertThat(foundByTagsNews, notNullValue());
        assertThat(foundByTagsNews.size(), is(1));
    }

    @Test
    public void addComment() throws DAOException {
        News news = newsDAO.findAll().get(0);
        Comment comment = new Comment("testComment", new Date(), news.getId());
        int commentId = commentDAO.insert(comment);
        Comment foundByIdComment = commentDAO.findById(commentId);
        assertThat(foundByIdComment, notNullValue());
        assertThat(foundByIdComment.getNewsId(), is(news.getId()));
    }

}
