package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    List<Map<String, Object>> queryAllRoles();

    //根据角色名称查询是否存在
    Role queryRoleByRoleName(String roleName);
}