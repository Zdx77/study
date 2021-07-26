package com.ouc.study.controller.interceptor;/*
 *文件名: MessageInterceptor
 *创建者: zdx
 *创建时间:2021/7/26 11:31
 *描述: TODO
 */

import com.ouc.study.entity.User;
import com.ouc.study.service.MessageService;
import com.ouc.study.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MessageInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private MessageService messageService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user != null && modelAndView != null){
            int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(),null );
            int noticeUnreadCount = messageService.findNoticeUnreadCount(user.getId(), null);
            modelAndView.addObject("allUnreadCount",letterUnreadCount + noticeUnreadCount);
        }
    }
}
