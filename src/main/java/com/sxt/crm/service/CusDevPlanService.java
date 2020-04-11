package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.SaleChanceMapper;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    @Resource
    private SaleChanceMapper saleChanceMapper;
    //添加数据功能
    public void saveCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         *    is_valid createDate  updateDate
         *
         *  3.执行添加  判断结果
         */
        //校验参数方法
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //设置默认参数值
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        //调用dao层方法，校验是否成功
        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"添加失败");
    }
    //参数校验方法
    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(saleChanceId==null || saleChanceMapper.selectByPrimaryKey(saleChanceId)==null,"请设置营销机会id");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"计划项内容不能为空!");
        AssertUtil.isTrue(null == planDate,"请指定计划项日期!");
    }


    //更新数据功能
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     id  非空 记录存在
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         updateDate
         *  3.执行更新  判断结果
         */
        AssertUtil.isTrue(cusDevPlan.getId()==null || selectByPrimaryKey(cusDevPlan.getId())==null,"更新数据不存在！");
        //校验参数方法
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //设置默认参数
        cusDevPlan.setUpdateDate(new Date());
        //调用service层方法
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"更新失败");
    }


    //删除功能
    public void deleteCusDevPlan(Integer id){
        CusDevPlan cusDevPlan=selectByPrimaryKey(id);
        AssertUtil.isTrue(id==null || cusDevPlan==null,"数据不存在！");
        //条件成立，设置为无效
        cusDevPlan.setIsValid(0);
        //调用dao层删除方法
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"删除失败！");
    }
}
