package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.CusDevPlanQuery;
import com.sxt.crm.service.CusDevPlanService;
import com.sxt.crm.vo.CusDevPlan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")//客户开发计划
public class CusDevPlanController extends BaseController {
    @Resource
    private CusDevPlanService cusDevPlanService;
    //页面转发
    @RequestMapping("index")
    public String index(){
        return "cus_dev_plan";
    }

    //分页查询
    @RequestMapping("list")
    @ResponseBody
    public Map<String ,Object> queryCusDevPlanParams(CusDevPlanQuery cusDevPlanQuery){
        //调用service层封装好的分页查询方法
        return cusDevPlanService.queryByParams(cusDevPlanQuery);
    }

    //添加数据功能
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCusDevPlan(CusDevPlan cusDevPlan){
        //调用service层方法
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        return success("添加成功！");
    }

    //更新数据
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        //调用service层方法
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("更新成功！");
    }

    //删除数据
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        //调用service层方法
        cusDevPlanService.deleteCusDevPlan(id);
        return success("删除成功！");
    }
}
