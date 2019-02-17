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
public interface PersonRepository extends CrudRepository<Person, Long> {

    /** 像写sql语句一样, 写方法名. 如果参数太多, 写起来也挺累的 */
    List<Person> findByEmailAddressAndLastname(String emailAddress, String lastname);
    // Enables the distinct flag for the query
    List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
    List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
    // Enabling ignoring case for an individual property
    List<Person> findByLastnameIgnoreCase(String lastname);
    // Enabling ignoring case for all suitable properties
    List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
    // Enabling static ORDER BY for a query
    List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
    List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
    List<Person> findByIdGreaterThan(long id);
    List<Person> findByIdBetween(long low, long high);
    //分页
    Page<Person> queryFirst10ByLastname(String lastname, Pageable pageable);

    //使用hql语句
    @Query("SELECT a FROM Person a WHERE a.id=:id")
    Person findByIdWithQuery(@Param("id") long id);

    //修改
    @Modifying
    @Transactional
    @Query("update Person a set a.firstname=:firstname WHERE a.id=:id")
    int updateWithQuery(@Param("id") long id, @Param("firstname") String firstname);
}