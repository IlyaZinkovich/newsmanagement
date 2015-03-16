package com.epam.news.model.entity;

import com.epam.news.model.entity.model.entity.News;
import com.epam.news.model.entity.service.NewsService;
import config.DBUnitConfig;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NewsTest extends DBUnitConfig {
    private NewsService service = new NewsService();
//    private EntityManager em = Persistence.createEntityManagerFactory("DBUnitEx").createEntityManager();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com/epam/news/model/entity/news/news-data.xml"));

        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    public NewsTest(String name) {
        super(name);
    }

    @Test
    public void testFindAll() throws Exception {
        List<News> persons = service.findAll();

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com/epam/news/model/entity/news/news-data.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEquals(expectedData, actualData);
        Assert.assertEquals(expectedData.getTable("news").getRowCount(), persons.size());
    }

//    @Test
//    public void testSave() throws Exception {
//        Person person = new Person();
//        person.setName("Lilia");
//        person.setSurname("Vernugora");
//
//        service.save(person);
//
//        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
//                Thread.currentThread().getContextClassLoader()
//                        .getResourceAsStream("com/devcolibri/entity/person/person-data-save.xml"));
//
//        IDataSet actualData = tester.getConnection().createDataSet();
//
//        String[] ignore = {"id"};
//        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "person", ignore);
//    }
}
