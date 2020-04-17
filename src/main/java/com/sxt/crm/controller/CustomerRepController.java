package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.dao.CustomerLossMapper;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.CustomerRepQuery;
import com.sxt.crm.service.CustomerRepService;
import com.sxt.crm.vo.CustomerRep;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_rep")
public class CustomerRepController extends BaseController {

    @Resource
    private CustomerRepService customerRepService;

    @Resource
    private CustomerLossMapper customerLossMapper;

    //添加流失信息页面
    @RequestMapping("index")
    public String index(String  cusNo, Model model){
        model.addAttribute("customerLoss",customerLossMapper.queryCustomerLossByCusNo(cusNo));
        return "customer_rep";
    }

    //数据

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerRep(CustomerRepQuery customerRepQuery){

        return customerRepService.queryByParams(customerRepQuery);
    }

    //添加功能
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerRep(CustomerRep customerRep){
        customerRepService.saveCustomerRep(customerRep);
        return success("添加成功！");
    }

    //更新功能
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerRep(CustomerRep customerRep){
        customerRepService.updateCustomerRep(customerRep);
        return success("更新成功！");
    }

    //更新功能
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        customerRepService.deleteCustomerRep(id);
        return success("删除成功！");
    }
}
