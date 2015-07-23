package com.hyunkyoic.yisunsin.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.hyunkyoic.yisunsin.domain.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String>, QueryDslPredicateExecutor<Person>{
	
	Person findByName(String name);

}
