package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByNP(String userName);
}