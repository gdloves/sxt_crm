package com.sxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.base.BaseService;
import com.sxt.crm.enums.DevResult;
import com.sxt.crm.enums.StateStatus;
import com.sxt.crm.query.SaleChanceQuery;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.utils.PhoneUtil;
import com.sxt.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    //分页查询信息
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> result=new HashMap<String,Object>();
        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getRows());
        //分页查询功能
        PageInfo<SaleChance> pageInfo=new PageInfo<>(selectByParams(saleChanceQuery));
        result.put("total",pageInfo.getTotal());//页码
        result.put("rows",pageInfo.getList());//每一页的数据
        return result;
    }

    //营销机会管理：机会数据添加
    public void saveSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *   customerName:非空
         *   linkMan:非空
         *   linkPhone:非空 11位手机号
         * 2.设置相关参数默认值
         *      state:默认未分配  如果选择分配人  state 为已分配
         *      assignTime:如果  如果选择分配人   时间为当前系统时间
         *      devResult:默认未开发   如果选择分配人  devResult为开发中
         *      isValid :默认有效
         *      createDate updateDate:默认当前系统时间
         *  3.执行添加 判断结果
         */
        //参数校验方法
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //设置默认状态参数值
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        //校验是否传递指派人数据，设置状态给数据库
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());//指派时间
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        //调用dao层的添加功能，返回受影响行数进行判断
        AssertUtil.isTrue(insertSelective(saleChance)<1,"添加失败！");

    }

    //参数校验,设置返回提示
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"手机号不能为空！");
        //手机号码校验，封装了手机号校验方法
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)),"手机号格式不正确！");
    }


    //营销管理数据的修改
    public void updateSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *  id 记录存在校验
         *  customerName:非空
         *  linkMan:非空
         *  linkPhone:非空 11位手机号
         * 2. 设置相关参数值
         *      updateDate:系统当前时间
         *         原始记录 未分配 修改后改为已分配(由分配人决定)
         *            state 0->1
         *            assginTime 系统当前时间
         *            devResult 0-->1
         *         原始记录  已分配  修改后 为未分配
         *            state  1-->0
         *            assignTime  待定  null
         *            devResult 1-->0
         * 3.执行更新 判断结果
         */
        //判断选中的记录是否存在
        AssertUtil.isTrue(null==saleChance.getId(),"待更新记录不存在!");
        //查询原始数据
        SaleChance temp=selectByPrimaryKey(saleChance.getId());
        //判断是否存在原始记录
        AssertUtil.isTrue(temp==null,"待更新记录不存在!");
        //参数判断方法
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //原始记录 未分配 修改后改为已分配
        if(StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());//状态
            saleChance.setAssignTime(new Date());//指派时间
            saleChance.setDevResult(DevResult.DEVING.getStatus());//客户开发状态
        }else if(StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())){
            //原始记录  已分配  修改后 为未分配
            saleChance.setAssignMan("");//指派人
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }
        saleChance.setUpdateDate(new Date());//更新时间
        //调用dao层方法更新数据,返回受影响行数判断是否大于1
        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance)<1,"更新失败！");
    }


    //营销管理数据的删除
    public void deleteSaleChance(Integer[] ids){
        //判断id
        AssertUtil.isTrue(ids==null || ids.length==0,"请选择待删除的机会数据!");
        //如果不为空时，调用dao删除功能
        AssertUtil.isTrue(deleteBatch(ids)<ids.length,"删除失败！");
    }
}
