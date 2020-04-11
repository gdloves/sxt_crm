package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.exceptions.ParamsException;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.model.UserModel;
import com.sxt.crm.query.UserQuery;
import com.sxt.crm.service.UserService;
import com.sxt.crm.utils.LoginUserUtil;
import com.sxt.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    @Autowired
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

    //用户管理模块：页面展示
    @RequestMapping("user/index")
    public String index(){
        return "user";//视图
    }

    //分页查询用户信息
    @RequestMapping("user/list")
    @ResponseBody
    public Map<String,Object> queryUserParams(UserQuery userQuery){
       /* System.out.println("进来了");
        System.out.println(userService.queryByParams(userQuery));*/
        //调用service层方法
        return userService.queryByParams(userQuery);
    }

    //添加功能
    @RequestMapping("user/save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        //调用service层方法
        userService.saveUser(user);
        return success("添加成功！");
    }

    //更新功能
    @RequestMapping("user/update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        //调用service层方法
        userService.updateUser(user);
        return success("更新成功！");
    }

    //删除功能
    @RequestMapping("user/delete")
    @ResponseBody
    public ResultInfo deleteUser(@RequestParam(name = "id") Integer userId){
        /*System.out.println("进入删除功能");
        System.out.println(userId);*/
        //调用service层方法
        userService.deleteUser(userId);
        return success("删除成功！");
    }
}