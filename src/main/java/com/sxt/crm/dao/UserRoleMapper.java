package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {
    //角色的更新操作:查询次数
    public int countUserRoleByUserId(Integer userId);
    //删除角色操作
    public int deleteUserRoleByUserId(Integer userId);
}