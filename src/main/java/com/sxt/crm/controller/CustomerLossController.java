package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.query.CustomerLossQuery;
import com.sxt.crm.service.CustomerLossService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;

    //客户流失页面
    @RequestMapping("index")
    private String index(){
        return "customer_loss";
    }

    //流失数据展示
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLoss(CustomerLossQuery customerLossQuery){

        return customerLossService.queryByParams(customerLossQuery);
    }
}
