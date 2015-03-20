package com.epam.newsmanagement.model.entity;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import config.DBUnitDataSets;
import org.dbunit.Assertion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;

@ContextConfiguration("classpath:spring-test.xml")
public class NewsTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    public NewsDAO newsDAO;

    @Test
    @Rollback(false)
    @DBUnitDataSets(before = "news-data-initial.xml",
            after = "news-data-expected.xml")
    @DirtiesContext
    public void test1() throws DAOException {
        News news = new News("ss", "ff", "tt", new Date(), new Date());
        int id = newsDAO.insert(news);
//        Assertion.assertEqualsIgnoreCols(news,newsDAO.findById(id),"NEWS", new String[]{"news_id"});
    }
}












//
//import com.epam.newsmanagement.service.NewsService;
//import config.DBUnitConfig;
//import org.dbunit.Assertion;
//import org.dbunit.dataset.DataSetException;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.Date;
//import java.util.List;
//
//public class NewsTest extends DBUnitConfig {
//
//    ApplicationContext context = new ClassPathXmlApplicationContext("spring-module.xml");
//    private NewsService service;
////    private EntityManager em = Persistence.createEntityManagerFactory("DBUnitEx").createEntityManager();
//
//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//        beforeData = new FlatXmlDataSetBuilder().build(
//                Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("com.epam.newsmanagement.model.entity.com.epam.newsmanagement.model.entity/com.epam.newsmanagement.model.entity-data-initial.xml"));
//        service
////        tester.setDataSet(beforeData);
////        tester.onSetup();
//    }
//
//    public NewsTest(String name) {
//        super(name);
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        List<News> newsList = service.findAll();
//        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
//                Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("com.epam.newsmanagement.model.entity.com.epam.newsmanagement.model.entity/com.epam.newsmanagement.model.entity-data-initial.xml"));
//        IDataSet actualData = tester.getConnection().createDataSet();
//        String[] ignore = {"creation_date", "modification_date"};
//        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "com.epam.newsmanagement.model.entity", ignore);
//        Assert.assertEquals(expectedData.getTable("com.epam.newsmanagement.model.entity").getRowCount(), newsList.size());
//    }
//
//    @Test
//    public void testAdd() throws Exception {
//        News com.epam.newsmanagement.model.entity = new News("short", "full", "title", new Date(), new Date());
//        service.addNews(com.epam.newsmanagement.model.entity);
//        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
//                Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("com.epam.newsmanagement.model.entity.com.epam.newsmanagement.model.entity/com.epam.newsmanagement.model.entity-save.xml"));
//        IDataSet actualData = tester.getConnection().createDataSet();
//        String[] ignore = {"news_id"};
//        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "com.epam.newsmanagement.model.entity", ignore);
//    }
//}
