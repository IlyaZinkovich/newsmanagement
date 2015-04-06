package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.domain.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
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

import java.util.Date;
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
@DatabaseSetup("classpath:dbunitxml/author-data.xml")
public class AuthorDAOTest {

    @Autowired
    private AuthorDAO authorDAO;

    private Author testAuthor;
    private Author existingTestAuthor;
    private final int testDataSize = 2;

    @Before
    public void setUp() {
        testAuthor = new Author(3, "Dave", null);
        existingTestAuthor = new Author(1, "John", null);
    }

    @Test
    public void findAllSucceed() throws Exception {
        List<Author> foundAuthors = authorDAO.findAll();
        assertThat(foundAuthors.size(), is(testDataSize));
    }

    @Test
    public void insertAuthorSucceed() throws Exception {
        List<Author> authorsBefore = authorDAO.findAll();
        long generatedId = authorDAO.insert(testAuthor);
        assertThat(generatedId, greaterThan(0l));
        testAuthor.setId(generatedId);
        List<Author> authorsAfter = authorDAO.findAll();
        assertThat(authorsAfter.size(), is(authorsBefore.size() + 1));
        assertTrue(authorsAfter.contains(testAuthor));
    }

    @Test
    public void insertAuthorDoesNothingIfAuthorWithThisNameAlreadyExists() throws Exception {
        List<Author> foundAuthorsBefore = authorDAO.findAll();
        Author authorToInsert = foundAuthorsBefore.get(0);
        long generatedId = authorDAO.insert(authorToInsert);
        assertThat(generatedId, is(authorToInsert.getId()));
        List<Author> foundAuthorsAfter = authorDAO.findAll();
        assertThat(foundAuthorsBefore, is(foundAuthorsAfter));
    }

    @Test
    public void updateAuthorSucceed() throws Exception {
        List<Author> foundAuthorsBefore = authorDAO.findAll();
        Author authorToUpdate = foundAuthorsBefore.get(0);
        authorToUpdate.setExpired(new Date());
        authorToUpdate.setName(testAuthor.getName());
        authorDAO.update(authorToUpdate);
        Author updatedAuthor = authorDAO.findById(authorToUpdate.getId());
        assertEquals(authorToUpdate, updatedAuthor);
    }

    @Test
    public void deleteAuthorSucceed() throws Exception {
        List<Author> foundAuthorsBefore = authorDAO.findAll();
        Author authorToDelete = foundAuthorsBefore.get(0);
        authorDAO.delete(authorToDelete.getId());
        Author deletedAuthor = authorDAO.findById(authorToDelete.getId());
        assertThat(deletedAuthor, nullValue());
    }

    @Test
    public void findByNewsIdSucceed() throws Exception {
        Author foundAuthor = authorDAO.findByNewsId(1);
        assertThat(foundAuthor.getName(), is(existingTestAuthor.getName()));
    }

    @Test
    public void findByNameSucceed() throws Exception {
        Author foundAuthor = authorDAO.findByName(existingTestAuthor.getName());
        assertThat(foundAuthor.getName(), is(existingTestAuthor.getName()));
    }



}
