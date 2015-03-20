package com.epam.newsmanagement.model.entity;

import com.epam.newsmanagement.service.NewsService;
import config.DBUnitConfig;
import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class NewsTest extends DBUnitConfig {

    private NewsService service = new NewsService();
//    private EntityManager em = Persistence.createEntityManagerFactory("DBUnitEx").createEntityManager();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com.epam.newsmanagement.model.entity.news/news-data.xml"));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    public NewsTest(String name) {
        super(name);
    }

    @Test
    public void testFindAll() throws Exception {
        List<News> newsList = service.findAll();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com.epam.newsmanagement.model.entity.news/news-data.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();
        String[] ignore = {"creation_date", "modification_date"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "news", ignore);
        Assert.assertEquals(expectedData.getTable("news").getRowCount(), newsList.size());
    }

    @Test
    public void testAdd() throws Exception {
        News news = new News("short", "full", "title", new Date(), new Date());
        service.addNews(news);
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com.epam.newsmanagement.model.entity.news/news-save.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();
        String[] ignore = {"news_id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "news", ignore);
    }
}