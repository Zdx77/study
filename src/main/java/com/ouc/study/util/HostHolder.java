package com.ouc.study.util;/*
 *文件名: HostHolder
 *创建者: zdx
 *创建时间:2021/7/21 10:32
 *描述: TODO
 */

import com.ouc.study.entity.User;
import org.springframework.stereotype.Component;

 /*
  持有用户信息，用于代替session对象
  */

@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
