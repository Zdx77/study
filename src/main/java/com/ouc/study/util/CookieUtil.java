package com.ouc.study.util;/*
 *文件名: CookieUtil
 *创建者: zdx
 *创建时间:2021/7/21 10:17
 *描述: TODO
 */

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static  String getValue(HttpServletRequest request,String name){
        if(request == null || name ==null){
        throw  new IllegalArgumentException("参数为空！");
    }
    Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
