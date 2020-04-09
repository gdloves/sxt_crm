package com.sxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.base.BaseService;
import com.sxt.crm.query.SaleChanceQuery;
import com.sxt.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

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
        result.put("totle",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());//每一页的数据
        return result;
    }

}
