package com.ouc.study.dao;/*
 *文件名: AlphaDaoMyBatisImpl
 *创建者: zdx
 *创建时间:2021/7/19 10:25
 *描述: TODO
 */

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao{

    @Override
    public String select() {
        return "Mybatis";
    }
}
