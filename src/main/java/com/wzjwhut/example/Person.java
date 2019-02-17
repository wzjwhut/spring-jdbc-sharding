package com.wzjwhut.example;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data //用于自动生成getter和setter, idea需要安装插件
public class Person {
    @Id
    private long id;

    //column名称映射规则: java使用驼峰格式, 数据库使用下划线格式， emailAddress将映射为email_address
    private String emailAddress;
    private String lastname;
    private String firstname;
}