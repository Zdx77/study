package com.ouc.study.dao;/*
 *文件名: UserMapper
 *创建者: zdx
 *创建时间:2021/7/19 16:34
 *描述: TODO
 */

import com.ouc.study.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectById(int id);

    User selectByName(String name);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id,int status);

    int updateHeader(int id,String headerUrl);

    int updatePassword(int id,String password);

}
