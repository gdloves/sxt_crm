package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.service.ModuleService;
import com.sxt.crm.service.PermissionService;
import com.sxt.crm.service.UserService;
import com.sxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private ModuleService moduleService;
    //登入页面
    @RequestMapping("index")
    public String index() {
        /* req.setAttribute("ctx",req.getContextPath());*/
        /*int num=1/0;*/
        /*if(1==1){
            throw new ParamsException("参数异常");
        }*/
        return "index";
    }

    //后端管理页面
    @RequestMapping("main")
    public String mains(HttpServletRequest req) {
        /*req.setAttribute("ctx",req.getContextPath());*/
        //获取用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //根据用户id查询受权限信息
        List<String> permissions = permissionService.queryUserHasRolesHasPermissions(userId);
        //权限信息
        req.getSession().setAttribute("permissions",permissions);
        //动态展示菜单列表
        req.getSession().setAttribute("modules",moduleService.queryUserHasRoleHasModuleDtos(userId));
        //查询到的信息存到作用域中
        req.setAttribute("user", userService.selectByPrimaryKey(userId));
        return "main02";//视图
    }
}
