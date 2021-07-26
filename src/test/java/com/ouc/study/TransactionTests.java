package com.ouc.study;/*
 *文件名: TransactionTests
 *创建者: zdx
 *创建时间:2021/7/22 10:40
 *描述: TODO
 */

import com.ouc.study.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = StudyApplication.class)
public class TransactionTests {

    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave() {
//        Object obj = alphaService.savel();
//        System.out.println(obj);
    }

    @Test
    public void testSave2() {
//        Object obj = alphaService.save2();
//        System.out.println(obj);
    }

}
