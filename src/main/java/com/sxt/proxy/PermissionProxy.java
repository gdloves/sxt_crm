package com.sxt.proxy;

import com.sxt.crm.annotaions.RequirePermission;
import com.sxt.crm.exceptions.AuthFailedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect//切面
public class PermissionProxy {

 /*   @Pointcut("@annotation(com.shsxt.crm.annotaions.RequirePermission)")
    public void abc(){}*/
    @Autowired
    private HttpSession session;




    //通知
    @Around(value = "@annotation(com.sxt.crm.annotaions.RequirePermission)")
    public  Object around(ProceedingJoinPoint pjp) throws Throwable {
        //权限
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if(null == permissions || permissions.size()==0){
            throw  new AuthFailedException();
        }
        Object result =null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        //获取RequirePermission注解
        RequirePermission requirePermission = methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        //权限码值
        if(!(permissions.contains(requirePermission.code()))){
            throw  new AuthFailedException();
        }
        result= pjp.proceed();
        return result;
    }

}
