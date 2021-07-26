package com.ouc.study.event;/*
 *文件名: EventProducer
 *创建者: zdx
 *创建时间:2021/7/25 16:34
 *描述: TODO
 */

import com.alibaba.fastjson.JSONObject;
import com.ouc.study.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fileEvent(Event event){
        //将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }


}
