package com.epam.newsmanagement.dao.implementation;

import com.epam.newsmanagement.domain.Tag;
import com.epam.newsmanagement.dao.TagDAO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
@DatabaseSetup("classpath:dbunitxml/tag-data.xml")
public class TagDAOTest {

    @Autowired
    private TagDAO tagDAO;

    private Tag testTag;
    private Tag existingTestTag;
    private List<Tag> testTagsList;
    private final int testDataSize = 3;

    @Before
    public void setUp() {
        testTag = new Tag(4, "Fashion");
        existingTestTag = new Tag(1, "Politics");
        testTagsList = new LinkedList<>();
        testTagsList.add(testTag);
        testTagsList.add(existingTestTag);
    }

    @Test
    public void findAllSucceed() throws Exception {
        List<Tag> foundTags = tagDAO.findAll();
        assertThat(foundTags.size(), is(testDataSize));
    }

    @Test
    public void insertTagSucceed() throws Exception {
        List<Tag> tagsBefore = tagDAO.findAll();
        long generatedId = tagDAO.insert(testTag);
        assertThat(generatedId, greaterThan(0l));
        testTag.setId(generatedId);
        List<Tag> tagsAfter = tagDAO.findAll();
        assertThat(tagsAfter.size(), is(tagsBefore.size() + 1));
        assertTrue(tagsAfter.contains(testTag));
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunitxml/tag-data-expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void insertListSucceed() throws Exception {
        tagDAO.insert(testTagsList);
    }

    @Test
    public void insertTagDoesNothingIfTagWithThisNameAlreadyExists() throws Exception {
        List<Tag> foundTagsBefore = tagDAO.findAll();
        Tag TagToInsert = foundTagsBefore.get(0);
        long generatedId = tagDAO.insert(TagToInsert);
        assertThat(generatedId, is(TagToInsert.getId()));
        List<Tag> foundTagsAfter = tagDAO.findAll();
        assertThat(foundTagsBefore, is(foundTagsAfter));
    }

    @Test
    public void updateTagSucceed() throws Exception {
        List<Tag> foundTagsBefore = tagDAO.findAll();
        Tag tagToUpdate = foundTagsBefore.get(0);
        tagToUpdate.setName(testTag.getName());
        tagDAO.update(tagToUpdate);
        Tag updatedTag = tagDAO.findById(tagToUpdate.getId());
        assertEquals(tagToUpdate, updatedTag);
    }

    @Test
    public void deleteTagSucceed() throws Exception {
        List<Tag> foundTagsBefore = tagDAO.findAll();
        Tag TagToDelete = foundTagsBefore.get(0);
        tagDAO.delete(TagToDelete.getId());
        Tag deletedTag = tagDAO.findById(TagToDelete.getId());
        assertThat(deletedTag, nullValue());
    }

    @Test
    public void findByNewsIdSucceed() throws Exception {
        List<Tag> foundTags = tagDAO.findByNewsId(1);
        assertTrue(foundTags.contains(existingTestTag));
        assertThat(foundTags.size(), is(2));
    }

    @Test
    public void findByNewsIdReturnsNullIfNewsDoesNotHaveTags() throws Exception {
        List<Tag> foundTags = tagDAO.findByNewsId(5);
        assertThat(eq(foundTags), nullValue());
    }

    @Test
    public void findByNameSucceed() throws Exception {
        Tag foundTag = tagDAO.findByName("Sports");
        assertThat(foundTag.getName(), is("Sports"));
    }

    @Test
    public void findByNameReturnsNullIfTagIsNotFound() throws Exception {
        Tag foundTag = tagDAO.findByName("Nature");
        assertThat(foundTag, nullValue());
    }

}
