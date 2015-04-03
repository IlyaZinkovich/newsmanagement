package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import com.epam.newsmanagement.service.interfaces.TagService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceTest {

    @Mock
    private TagDAO tagDAO;

    private TagService tagService;

    private Tag testTag;
    private List<Tag> testTags;
    private List<Long> testGeneratedIdList;
    private long testNewsId;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        tagService = new TagServiceImpl(tagDAO);
        testTag = new Tag(1, "tag");
        testTags = new LinkedList<>();
        testTags.add(testTag);
        testTags.add(new Tag(2, "second"));
        testTags.add(new Tag(3, "third"));
        testGeneratedIdList = LongStream.rangeClosed(1, testTags.size()).mapToObj(l -> l)
                .collect(Collectors.toList());
        testNewsId = 1;
    }

    @Test
    public void addTagSucceed() throws Exception {
        when(tagDAO.insert(testTag)).thenReturn(1l);
        long generatedId = tagService.addTag(testTag);
        assertThat(generatedId, is(greaterThan(0l)));
        verify(tagDAO).insert(testTag);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void addTagsSucceed() throws Exception {
        when(tagDAO.insert(testTags)).thenReturn(testGeneratedIdList);
        List<Long> generatedIdList = tagService.addTags(testTags);
        assertThat(generatedIdList, is(testGeneratedIdList));
        assertThat(generatedIdList.stream().allMatch(id -> id > 0), is(true));
        verify(tagDAO).insert(testTags);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void editTagSucceed() throws Exception {
        tagService.editTag(testTag);
        verify(tagDAO).update(testTag);
        verifyNoMoreInteractions(tagDAO);
    }

    @Test
    public void deleteTagSucceed() throws Exception {
        tagService.deleteTag(testTag.getId());
        verify(tagDAO).delete(testTag.getId());
        verifyNoMoreInteractions(tagDAO);
    }


    @Test
    public void findByNewsIdSucceed() throws Exception {
        when(tagDAO.findByNewsId(testNewsId)).thenReturn(testTags);
        List<Tag> foundTags = tagService.findByNewsId(testNewsId);
        assertThat(foundTags, is(testTags));
        verify(tagDAO).findByNewsId(testNewsId);
        verifyNoMoreInteractions(tagDAO);
    }
}
