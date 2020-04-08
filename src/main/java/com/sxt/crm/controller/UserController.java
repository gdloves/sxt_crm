package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.service.UserService;
import com.sxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("user/queryUserId")
    @ResponseBody
    public User queryUserId(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }
}
