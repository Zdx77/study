package com.ouc.study;/*
 *文件名: KafkaTests
 *创建者: zdx
 *创建时间:2021/7/25 15:52
 *描述: TODO
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = StudyApplication.class)
public class KafkaTests {

    @Autowired
    private  kafkaProducer kafkaProducer;
    @Test
    public void testKafka(){
        kafkaProducer.sendMessage("test","你好");
        kafkaProducer.sendMessage("test","在吗");
        try {
            Thread.sleep(1000*10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

@Component
class kafkaProducer{

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic,String content){
        kafkaTemplate.send(topic,content );
    }
}

@Component
class kafkaConsumer{

    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record){
        System.out.println(record.value());
    }
}
