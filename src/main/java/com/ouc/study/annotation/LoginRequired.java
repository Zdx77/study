package com.ouc.study.annotation;/*
 *文件名: LoginRequired
 *创建者: zdx
 *创建时间:2021/7/21 17:44
 *描述: TODO
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
