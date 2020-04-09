package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.exceptions.ParamsException;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.model.UserModel;
import com.sxt.crm.service.UserService;
import com.sxt.crm.utils.LoginUserUtil;
import com.sxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    //根据id查询数据
    @RequestMapping("user/queryUserId")
    @ResponseBody
    public User queryUserId(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }

    //用户登入功能
    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        UserModel userModel=userService.login(userName,userPwd);
        //如果报错执行全局异常
        return success("用户登录成功",userModel);
    }


    //修改密码
    @RequestMapping("user/updatePassword")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest req,String oldPassword,String newPassword,String confirmPassword){
        ResultInfo resultInfo=new ResultInfo();
        /*System.out.println("进来了");
        System.out.println(oldPassword);
        System.out.println(newPassword);
        System.out.println(confirmPassword);*/
        userService.updateUserPwd(LoginUserUtil.releaseUserIdFromCookie(req), oldPassword, newPassword, confirmPassword);
        //如果报错执行全局异常
        return success("密码更新成功");
    }
}
