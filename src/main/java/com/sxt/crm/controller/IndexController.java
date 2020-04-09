package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.exceptions.ParamsException;
import com.sxt.crm.service.UserService;
import com.sxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;
    //登入页面
    @RequestMapping("index")
    public String index(){
       /* req.setAttribute("ctx",req.getContextPath());*/
        /*int num=1/0;*/
        /*if(1==1){
            throw new ParamsException("参数异常");
        }*/
        return "index";
    }

    //后端管理页面
    @RequestMapping("main")
    public String mains(HttpServletRequest req){
        /*req.setAttribute("ctx",req.getContextPath());*/

        //获取用户id
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(req);
        //查询到的信息存到作用域中
        req.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";//视图
    }
}
