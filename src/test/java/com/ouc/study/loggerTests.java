package com.ouc.study;/*
 *文件名: loggerTests
 *创建者: zdx
 *创建时间:2021/7/19 22:34
 *描述: TODO
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = StudyApplication.class)
public class loggerTests {

    private static final Logger logger = LoggerFactory.getLogger(loggerTests.class);

    @Test
    public void testLogger(){
        System.out.println(logger.getName());
        logger.debug("debug Log");
        logger.info("info Log");
        logger.warn("warn Log");
        logger.error("error Log");
    }
}
