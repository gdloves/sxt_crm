package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.CustomerMapper;
import com.sxt.crm.dao.UserMapper;
import com.sxt.crm.enums.CustomerServeStatus;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class CustomerServeService extends BaseService<CustomerServe,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private UserMapper userMapper;

    //服务添加
    public  void saveCustomerServe(CustomerServe customerServe){
        //判断id值是否存在，来进行更新或添加操作
        if(customerServe.getId()==null){
            /**  服务添加操作
             * 1.参数校验
             *     客户名  非空
             *     客户类型  非空
             * 2.添加默认值
             *    state  设置状态值
             *    isValid  createDate updateDate
             *  3.执行添加 判断结果
             */
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()),"客户名不能为空！");
            AssertUtil.isTrue(customerMapper.queryCustomerName(customerServe.getCustomer())==null,"当前客户不存在！");
            AssertUtil.isTrue(customerServe.getServeType()==null,"服务类型不能为空！");
            //设置默认值
            customerServe.setIsValid(1);
            customerServe.setCreateDate(new Date());
            customerServe.setUpdateDate(new Date());
            //枚举：设置状态
            customerServe.setState(CustomerServeStatus.CREATED.getState());
            //添加方法
            AssertUtil.isTrue(insertSelective(customerServe)<1,"服务记录添加失败！");
        }else {
            //更新操作
            CustomerServe temp=selectByPrimaryKey(customerServe.getId());
            AssertUtil.isTrue(temp==null,"该记录不存在！");
            if(customerServe.getState().equals(CustomerServeStatus.ASSIGNED.getState())){
                //服务分配
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner()) || (null == userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner()))),"分配用户不存在！");
                customerServe.setUpdateDate(new Date());
                customerServe.setAssignTime(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"更新失败！");

            }if(customerServe.getState().equals(CustomerServeStatus.PROCED.getState())){
                // 服务处理
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"请指定处理内容!");
                customerServe.setServiceProceTime(new Date());
                customerServe.setUpdateDate(new Date());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务处理失败!");
            }if(customerServe.getState().equals(CustomerServeStatus.FEED_BACK.getState())){
                // 服务反馈
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"请指定反馈内容!");
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"请指定反馈满意度!");
                customerServe.setUpdateDate(new Date());
                customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
                AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务反馈失败!");
            }
        }

    }
}
