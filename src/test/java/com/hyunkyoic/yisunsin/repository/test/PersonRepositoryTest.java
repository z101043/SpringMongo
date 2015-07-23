package com.hyunkyoic.yisunsin.repository.test;

import static org.junit.Assert.*;
import initializer.AppInitializer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hyunkyoic.yisunsin.domain.Person;
import com.hyunkyoic.yisunsin.repository.PersonRepository;

import config.MongoConfig;
import config.MvcConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class, MongoConfig.class })
public class PersonRepositoryTest {

	@Autowired PersonRepository personRepository;
	
	@Test
	public void addPersonTest() {
		
		Person person = new Person("현교3");
		
		personRepository.save(person);
		
	}

}
