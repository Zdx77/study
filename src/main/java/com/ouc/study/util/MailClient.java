package com.ouc.study.util;/*
 *文件名: MailClient
 *创建者: zdx
 *创建时间:2021/7/20 9:39
 *描述: TODO
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void  sendMail(String to,String submit,String content){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(submit);
            helper.setText(content,true);
            mailSender.send(helper.getMimeMessage());
        }catch (MessagingException e){
            logger.error("发送邮件失败"+e.getMessage());
        }

    }
}
