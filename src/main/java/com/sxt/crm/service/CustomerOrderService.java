package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.CustomerOrderMapper;
import com.sxt.crm.query.CustomerOrderQuery;
import com.sxt.crm.vo.CustomerOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {
    @Resource
    private CustomerOrderMapper customerOrderMapper;

    public  Map<String, Object> queryOrderDetailByOrderId(Integer orderId){

        return customerOrderMapper.queryOrderDetailByOrderId(orderId);
    }

}
