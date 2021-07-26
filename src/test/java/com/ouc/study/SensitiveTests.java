package com.ouc.study;/*
 *文件名: SensitiveTests
 *创建者: zdx
 *创建时间:2021/7/21 19:22
 *描述: TODO
 */

import com.ouc.study.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = StudyApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public  void testSensitiveFilter(){
        String text = "这里可以赌博，可以嫖娼，可以吸毒，可以开票，哈哈哈嫖娼";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这里可以▽赌▽博▽，可以▽嫖▽娼，可以吸▽毒▽，可以▽开▽▽▽票▽，哈哈哈嫖▽▽娼";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
