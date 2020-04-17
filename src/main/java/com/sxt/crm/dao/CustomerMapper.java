package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    //根据名称查询信息
    public Customer queryCustomerName(String name);

    //更新status状态
    public int updateCustomerStateByIds(List<Integer> lossCusIds);

    //从客户表中查询流水客户
    public List<Customer> queryLossCustomers();
}