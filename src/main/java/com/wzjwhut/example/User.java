package com.wzjwhut.example;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data //用于自动生成getter和setter, idea需要安装插件
public class User {
    @Id
    private int id;

}