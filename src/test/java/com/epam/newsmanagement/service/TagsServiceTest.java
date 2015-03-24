package com.epam.newsmanagement.service;

import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TagsServiceTest {

}