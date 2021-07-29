package com.ouc.study.config;/*
 *文件名: ThreadPoolConfig
 *创建者: zdx
 *创建时间:2021/7/28 13:30
 *描述: TODO
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
