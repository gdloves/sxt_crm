package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.CustomerServeQuery;
import com.sxt.crm.service.CustomerServeService;
import com.sxt.crm.utils.LoginUserUtil;
import com.sxt.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController{

    @Resource
    private CustomerServeService customerServeService;
    //服务页面
    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if(type==1){
            return "customer_serve_create";
        }else if(type==2){
            return "customer_serve_assign";
        }else if(type==3){
            return "customer_serve_proce";
        }else if(type==4){
            return "customer_serve_feed_back";
        }else if(type==5){
            return "customer_serve_archive";
        }else{
            return "";
        }
    }

    //服务添加和更新功能
    @RequestMapping("saveOrUpdateCustomerServe")
    @ResponseBody
    public ResultInfo saveCustomerServe(CustomerServe customerServe){
        customerServeService.saveCustomerServe(customerServe);
        return success("添加成功！");
    }


    //服务信息展示
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerServeByParams(Integer falg,HttpServletRequest req,CustomerServeQuery customerServeQuery){
        System.out.println();
        //服务处理：falg标识
        if(falg!=null && falg==1){
            //获取cookie的分配人查询信息
            customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(req));
        }
        return customerServeService.queryByParams(customerServeQuery);
    }


}
