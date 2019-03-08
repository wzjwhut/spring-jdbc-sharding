package com.wzjwhut.example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


}