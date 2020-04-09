package com.sxt.crm.interceptors;

import com.sxt.crm.exceptions.NoLoginException;
import com.sxt.crm.service.UserService;
import com.sxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    //拦截器：重写preHandle
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //判断cookie是否存在
        //获取cookie的id
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        //判断是否存在，放行
        /*if(userId==0 || userService.selectByPrimaryKey(userId)==null){
            //不存在时，重定向跳转到登入页面
            response.sendRedirect(request.getContextPath()+"/index");
            return false;//拦截
        }*/
        //前台跳转，使用全局异常实现视图页面
        if(userId==0 || userService.selectByPrimaryKey(userId)==null){
            //由全局异常去处理
            throw new NoLoginException("请先登入！");
        }
        //放行
        return true;
    }
}
