package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.query.SaleChanceQuery;
import com.sxt.crm.service.SaleChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;
    //跳转视图
    @RequestMapping("index")
    public String index(){

        return "sale_chance";
    }

    //响应数据
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }
}
