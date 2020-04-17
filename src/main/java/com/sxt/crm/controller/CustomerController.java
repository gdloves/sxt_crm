package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.CustomerQuery;
import com.sxt.crm.service.CustomerServer;
import com.sxt.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerServer customerServer;

    //客户信息管理页面
    @RequestMapping("index")
    public String index(){
        return "customer";
    }

    //分页查询客户信息
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryAllCustomer(CustomerQuery customerQuery){
        //分页方法
        return customerServer.queryByParams(customerQuery);
    }

    //添加功能
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomer(Customer customer){
        customerServer.saveCustomer(customer);
        return success("客户添加成功！");
    }

    //更新功能
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer){
        customerServer.updateCustomer(customer);
        return success("客户更新成功！");
    }

    //删除功能
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer id){
        customerServer.deleteCustomer(id);
        return success("客户删除成功！");
    }

    //订单详情界面
    @RequestMapping("order_info")
    public String showOrder(Integer cid,Model model){
        model.addAttribute("customer",customerServer.selectByPrimaryKey(cid));
        return "customer_order";
    }
}
