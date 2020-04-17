package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.CustomerLoss;

public interface CustomerLossMapper extends BaseMapper<CustomerLoss,Integer> {

    //根据cus_no查询信息
    public CustomerLoss queryCustomerLossByCusNo(String cusNo);
}