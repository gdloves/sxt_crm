package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.query.CustomerOrderQuery;
import com.sxt.crm.service.CustomerOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("order")
public class CustomerOrderController extends BaseController {
    @Resource
    private CustomerOrderService customerOrderService;

    //客户订单表格详情展示
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> customerOrder(CustomerOrderQuery customerOrderQuery){
        // System.out.println("cid:"+customerOrderQuery.getCid());
        //分页多条件查询
        return customerOrderService.queryByParams(customerOrderQuery);
    }

    //详情查看的订单信息
    @RequestMapping("queryOrderDetailByOrderId")
    @ResponseBody
    public Map<String,Object> queryOrderDetailByOrderId(Integer orderId){

        return customerOrderService.queryOrderDetailByOrderId(orderId);
    }





}
