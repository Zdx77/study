package com.ouc.study.dao;/*
 *文件名: AlphaDaoHibernateImpl
 *创建者: zdx
 *创建时间:2021/7/19 10:22
 *描述: TODO
 */

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
