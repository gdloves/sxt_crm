package com.sxt.crm.dao;


import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.CustomerOrder;

import java.util.Map;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    public Map<String,Object> queryOrderDetailByOrderId(Integer orderId);

    //查询订单最后时间
    public CustomerOrder queryLastCustomerOrderByCusId(Integer id);
}