package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.annotaions.RequirePermission;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.SaleChanceQuery;
import com.sxt.crm.service.SaleChanceService;
import com.sxt.crm.service.UserService;
import com.sxt.crm.utils.LoginUserUtil;
import com.sxt.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;
    @Resource
    private UserService userService;
    //跳转视图
    @RequestMapping("index")
    public String index(){

        return "sale_chance";
    }

    //营销管理数据
    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "101001")
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    //营销管理添加数据
    @RequestMapping("save")
    @ResponseBody
    @RequirePermission(code = "101002")
    public ResultInfo saveSaleChance(HttpServletRequest req, SaleChance saleChance){
        //获取cookie中的id，查询用户信息中的创建人,设置创建人数据
        saleChance.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(req)).getTrueName());
        //调用service层方法
        saleChanceService.saveSaleChance(saleChance);
        return success("添加成功！");

    }

    //营销管理修改数据
    @RequestMapping("update")
    @ResponseBody
    @RequirePermission(code = "101004")
    public ResultInfo updateSaleChance(SaleChance saleChance){
        System.out.println("进入修改功能");

        //调用service层方法
        saleChanceService.updateSaleChance(saleChance);
        //成功
        return success("修改成功！");
    }

    //营销管理删除数据
    @RequestMapping("delete")
    @ResponseBody
    @RequirePermission(code = "101003")
    public ResultInfo deleteSaleChance(Integer[] ids){
        //调用service层方法
        saleChanceService.deleteSaleChance(ids);
        //成功
        return success("删除成功！");
    }
}
