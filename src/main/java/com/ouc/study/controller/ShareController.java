package com.ouc.study.controller;/*
 *文件名: ShareController
 *创建者: zdx
 *创建时间:2021/7/28 20:28
 *描述: TODO
 */

import com.ouc.study.entity.Event;
import com.ouc.study.event.EventProducer;
import com.ouc.study.util.StudyConstant;
import com.ouc.study.util.StudyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ShareController implements StudyConstant {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private EventProducer eventProducer;

    @Value("${study.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @Value("${qiniu.bucket.share.url}")
    private String shareBucketUrl;

    @RequestMapping(path = "/share",method = RequestMethod.GET)
    @ResponseBody
    public String share(String htmlUrl){
        //文件名
        String fileName = StudyUtil.generateUUID();

        //异步的生成长图
        Event event = new Event()
                .setTopic(TOPIC_SHARE)
                .setData("htmlUrl",htmlUrl)
                .setData("fileName",fileName)
                .setData("suffix",".png");
        eventProducer.fileEvent(event);

        //返回访问路径
        Map<String,Object> map = new HashMap<>();
//        map.put("shareUrl",domain + contextPath+ "/share/image/" + fileName);
        map.put("shareUrl",shareBucketUrl+"/"+fileName);

        return StudyUtil.getJSONString(0,null,map);
    }

    //废弃
    //获取长图
    @RequestMapping(path = "/share/image/{fileName}",method = RequestMethod.GET)
    public void getShareImage(@PathVariable("fileName") String fileName, HttpServletResponse response){
        if(StringUtils.isBlank(fileName)){
            throw new IllegalArgumentException("文件名不能为空！");
        }

        response.setContentType("image/png");
        File file = new File(wkImageStorage + "/" + fileName + ".png");

        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int b = 0;
            while( (b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        }catch (IOException e){
            logger.error("获取长图失败：" + e.getMessage());
        }

    }

}
