package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByNP(String userName);

    //分配人
    public List<Map<String,Object>> queryAllCustomerManager();
}