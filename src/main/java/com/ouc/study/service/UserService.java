package com.ouc.study.service;/*
 *文件名: UserService
 *创建者: zdx
 *创建时间:2021/7/19 20:03
 *描述: TODO
 */

import com.ouc.study.dao.UserMapper;
import com.ouc.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}
