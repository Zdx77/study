package com.ouc.study.controller;/*
 *文件名: AlphaController
 *创建者: zdx
 *创建时间:2021/7/19 9:52
 *描述: TODO
 */

import com.ouc.study.service.AlphaService;
import com.ouc.study.util.StudyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.*;
import javax.xml.xpath.XPath;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getDate(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath()); //请求路径
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        //获取响应数据
        response.setContentType("text/html;charset = utf-8");
        try(
                PrintWriter writer = response.getWriter();
        ){
            writer.write("<h1>学习</h1>");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Get请求

    // 一种  /student?current=1&limit=20
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") int currnt,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit){
        System.out.println(currnt);
        System.out.println(limit);
        return "some students";
    }

    //另一种  /student/123
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    //Post请求
    // 为什么不用get提交数据？ 1.请求数据都在明面上 2、长度有限制，太长就放不下

    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","北京大学");
        model.addAttribute("age",100);
        return "/demo/view";
    }

    //响应json数据（异步请求）
    // java对象 -> json字符串 -> js对象

    @RequestMapping(path ="/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age","23");
        emp.put("salary","8000");
        return emp;
    }

    @RequestMapping(path ="/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>>getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age","23");
        emp.put("salary","8000");
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李四");
        emp.put("age","24");
        emp.put("salary","9000");
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age","25");
        emp.put("salary","10000");
        list.add(emp);

        return list;
    }

    //cookie示例

    @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        // 创建cookie
        Cookie cookie = new Cookie("code", StudyUtil.generateUUID());
        // 设置cookie生效的范围
        cookie.setPath("/study/alpha");
        // 设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "get cookie";
    }

    // session示例

    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    //ajax示例
    @RequestMapping(path = "/ajax",method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name,String age){
        System.out.println(name);
        System.out.println(age);
        return StudyUtil.getJSONString(0,"操作成功！");
    }

}
