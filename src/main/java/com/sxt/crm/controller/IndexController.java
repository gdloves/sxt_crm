package com.sxt.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    //登入页面
    @RequestMapping("index")
    public String index(HttpServletRequest req){
        req.setAttribute("ctx",req.getContextPath());
        return "index";
    }

    //后端管理页面
    @RequestMapping("main")
    public String mains(HttpServletRequest req){
        req.setAttribute("ctx",req.getContextPath());
        return "main";
    }
}
