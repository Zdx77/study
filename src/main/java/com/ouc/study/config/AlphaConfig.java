package com.ouc.study.config;/*
 *文件名: AlphaConfig
 *创建者: zdx
 *创建时间:2021/7/19 10:42
 *描述: TODO
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class AlphaConfig {
    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat对象被装配到容器里(@Bean) bean的名字是方法名 simpleDateFormat
    }
}
