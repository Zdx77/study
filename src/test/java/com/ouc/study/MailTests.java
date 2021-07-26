package com.ouc.study;/*
 *文件名: MailTests
 *创建者: zdx
 *创建时间:2021/7/20 13:27
 *描述: TODO
 */

import com.ouc.study.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = StudyApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTestMail(){
        mailClient.sendMail("853570020@qq.com","Test","welcome");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","sunday");

        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("853570020@qq.com","html",content);

    }
}
