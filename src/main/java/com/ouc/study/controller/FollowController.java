package com.ouc.study.controller;/*
 *文件名: FollowController
 *创建者: zdx
 *创建时间:2021/7/24 16:07
 *描述: TODO
 */

import com.ouc.study.entity.Event;
import com.ouc.study.entity.Page;
import com.ouc.study.entity.User;
import com.ouc.study.event.EventProducer;
import com.ouc.study.service.FollowService;
import com.ouc.study.service.UserService;
import com.ouc.study.util.HostHolder;
import com.ouc.study.util.StudyConstant;
import com.ouc.study.util.StudyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController implements StudyConstant {

    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = "/follow",method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType,int entityId){
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType,entityId);

        //触发关注事件
        Event event = new Event()
                .setTopic(TOPIC_FOLLOW)
                .setUserId(hostHolder.getUser().getId())
                .setEntityId(entityId)
                .setEntityUserId(entityId);

        eventProducer.fileEvent(event);
        return StudyUtil.getJSONString(0,"已关注！");
    }

    @RequestMapping(path = "/unfollow",method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType,int entityId){
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType,entityId);
        return StudyUtil.getJSONString(0,"已取消关注！");
    }

    @RequestMapping(path = "/followees/{userId}",method = RequestMethod.GET)
    public String getFollowees(@PathVariable("userId") int userId, Page page, Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw  new RuntimeException("该用户不存在！");
        }
        model.addAttribute("user",user);

        page.setLimit(5);
        page.setPath("/followees" +userId);
        page.setRows((int)followService.findFolloweeCount(userId,ENTITY_TYPE_USER));

        List<Map<String,Object>> userList = followService.findFollowees(userId,page.getOffset(),page.getLimit());
        if(userList != null){
            for(Map<String,Object> map:userList){
                User u =(User) map.get("user");
                map.put("hasFollowed",hasFollowed(u.getId()));
            }
        }
        model.addAttribute("users",userList);

        return "/site/followee";
    }

    @RequestMapping(path = "/followers/{userId}",method = RequestMethod.GET)
    public String getFollowers(@PathVariable("userId") int userId, Page page, Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw  new RuntimeException("该用户不存在！");
        }
        model.addAttribute("user",user);

        page.setLimit(5);
        page.setPath("/followers" +userId);
        page.setRows((int)followService.findFollowerCount(ENTITY_TYPE_USER,userId));

        List<Map<String,Object>> userList = followService.findFollowers(userId,page.getOffset(),page.getLimit());
        if(userList != null){
            for(Map<String,Object> map:userList){
                User u =(User) map.get("user");
                map.put("hasFollowed",hasFollowed(u.getId()));
            }
        }
        model.addAttribute("users",userList);

        return "/site/follower";
    }

    private boolean hasFollowed(int userId){
        if(hostHolder.getUser() == null){
            return false;
        }
        return followService.hasFollowed(hostHolder.getUser().getId(),StudyConstant.ENTITY_TYPE_USER,userId);
    }

}
