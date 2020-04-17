package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.CustomerLossMapper;
import com.sxt.crm.dao.CustomerMapper;
import com.sxt.crm.dao.CustomerOrderMapper;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.utils.PhoneUtil;
import com.sxt.crm.vo.Customer;
import com.sxt.crm.vo.CustomerLoss;
import com.sxt.crm.vo.CustomerOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServer extends BaseService<Customer,Integer> {
    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    @Resource
    private CustomerLossMapper customerLossMapper;
    //客户添加
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomer(Customer customer){
        /**
         * 1.参数校验
         *    客户名称 name 非空  不可重复
         *    phone 联系电话  非空  格式符合规范
         *    法人  非空
         * 2.默认值设置
         *     isValid  state  cteaetDate  updadteDate
         *      khno 系统生成 唯一  (uuid| 时间戳 | 年月日时分秒  雪花算法)
         *3.执行添加  判断结果
         */
        //参数校验方法
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        //唯一性
        AssertUtil.isTrue(customerMapper.queryCustomerName(customer.getName())!=null,"客户已存在！");
        //设置默认值
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        //khno 系统生成 唯一  (uuid| 时间戳 | 年月日时分秒  雪花算法)
        String khno = "KH_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        //调用添加方法
        AssertUtil.isTrue(insertSelective(customer)<1,"客户添加失败！");
    }

    //参数校验
    private void checkParams(String name, String phone, String fr) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空！");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"手机号码格式不正确！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人不能为空！");

    }


    //客户更新功能
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer){
        /**
         * 1.参数校验
         *    记录存在校验
         *    客户名称 name 非空  不可重复
         *    phone 联系电话  非空  格式符合规范
         *    法人  非空
         * 2.默认值设置
         *      updadteDate
         *3.执行更新  判断结果
         */
        //判断选中记录
        AssertUtil.isTrue(selectByPrimaryKey(customer.getId())==null || customer.getId()==null,"该记录不存在！");
        //参数校验
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        Customer temp=customerMapper.queryCustomerName(customer.getName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(customer.getId())),"该客户已存在!");
        //设置默认值
        customer.setUpdateDate(new Date());
        //调用更新方法
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户更新失败！");
    }

    //删除功能
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer id){
        Customer customer=selectByPrimaryKey(id);
        AssertUtil.isTrue(id==null || customer==null,"该记录不存在！");
        /**
         * 如果客户被删除
         *     级联 客户联系人 客户交往记录 客户订单  被删除
         *
         * 如果客户被删除
         *     如果子表存在记录  不支持删除
         */
        customer.setIsValid(0);
        //调用方法
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户删除失败！");
    }



    //数据定时流失
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerState(){
        //从客户表中查询流水客户
        List<Customer> lossCustomers=customerMapper.queryLossCustomers();
        //判断是否为空
        if(lossCustomers!=null && lossCustomers.size()>0){
            List<CustomerLoss> customerLosses=new ArrayList<CustomerLoss>();
            //遍历客户id
            List<Integer> lossCusIds=new ArrayList<Integer>();
            lossCustomers.forEach(customer->{
                CustomerLoss customerLoss=new CustomerLoss();
                // 设置最后下单时间
                CustomerOrder lastCustomerOrder=customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId());
                if(lastCustomerOrder!=null){
                    customerLoss.setLastOrderTime(lastCustomerOrder.getOrderDate());
                }
                customerLoss.setCreateDate(new Date());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setIsValid(1);
                //  设置客户流失状态为暂缓流失状态
                customerLoss.setState(0);
                customerLoss.setUpdateDate(new Date());
                //将数据添加到数组中
                customerLosses.add(customerLoss);
                lossCusIds.add(customer.getId());
            });

            //流失表添加数据
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLosses)<customerLosses.size(),"客户流失数据流转失败!");
            //客户表更新status状态
            AssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCusIds)<lossCusIds.size(),"客户流失数据流转失败!");
        }

    }

}
