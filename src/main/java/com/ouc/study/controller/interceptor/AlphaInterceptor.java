package com.ouc.study.controller.interceptor;/*
 *文件名: AlphaInterceptor
 *创建者: zdx
 *创建时间:2021/7/21 9:44
 *描述: TODO
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AlphaInterceptor implements HandlerInterceptor {

    private static  final Logger logger = LoggerFactory.getLogger(AlphaInterceptor.class);
    // 在Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle:"+ handler.toString());
        return true;
    }

    // 在controller之后执行,TemplateEngine模板引擎之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle:"+handler.toString());
    }

    // TemplateEngine之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,  Exception ex) throws Exception {
        logger.debug("afterCompletion:"+handler.toString());
    }

}
