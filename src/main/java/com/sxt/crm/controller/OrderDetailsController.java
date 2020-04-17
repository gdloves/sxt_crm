package com.sxt.crm.controller;

import com.sxt.crm.query.OrderDetailsQuery;
import com.sxt.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("order_details")
public class OrderDetailsController {

    @Resource
    private OrderDetailsService orderDetailsService;

    //商品详情信息
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryOrderDetailsByParams(OrderDetailsQuery orderDetailsQuery){

        return orderDetailsService.queryByParams(orderDetailsQuery);
    }
}
