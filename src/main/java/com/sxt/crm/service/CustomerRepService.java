package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.CustomerMapper;
import com.sxt.crm.dao.CustomerRepMapper;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.vo.CustomerRep;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerRepService extends BaseService<CustomerRep,Integer> {
    @Resource
    private CustomerRepMapper customerRepMapper;

    //添加功能
    public void saveCustomerRep(CustomerRep customerRep){
        //判断参数
        AssertUtil.isTrue(StringUtils.isBlank(customerRep.getMeasure()),"不能为空！");
        //唯一
        AssertUtil.isTrue(customerRepMapper.queryByMeasure(customerRep.getMeasure())!=null,"不能重复！");
        //设置默认值
        customerRep.setIsValid(1);
        customerRep.setCreateDate(new Date());
        customerRep.setUpdateDate(new Date());
        customerRep.setLossId(customerRep.getLossId());
        //调用方法
        AssertUtil.isTrue(insertSelective(customerRep)<1,"添加失败！");
    }

    //更新功能
    public void updateCustomerRep(CustomerRep customerRep){
        //判断参数
        AssertUtil.isTrue(customerRepMapper.selectByPrimaryKey(customerRep.getId())==null || customerRep.getId()==null,"该记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(customerRep.getMeasure()),"不能为空！");
        //唯一
        CustomerRep temp=customerRepMapper.queryByMeasure(customerRep.getMeasure());
        if(temp!=null){
            AssertUtil.isTrue(!(temp.getId().equals(customerRep.getId())),"不能重复！");
        }
        //设置默认值
        customerRep.setUpdateDate(new Date());
        //调用方法
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerRep)<1,"更新失败！");
    }

    //删除功能
    public void deleteCustomerRep(Integer id){
        CustomerRep customerRep=selectByPrimaryKey(id);
        AssertUtil.isTrue(customerRep==null || id==null,"该记录不存在！");
        customerRep.setIsValid(0);
        //调用方法
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerRep)<1,"删除失败！");

    }
}
