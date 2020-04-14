package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {
    //统计次数:角色授权
    public int countPermissionByRoleId(Integer roleId);
    //删除授权
    public int deletePermissionsByRoleId(Integer roleId);

    //根据角色id查询受权限的信息
    List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    //根据用户id查询受权限信息
    List<String> queryUserHasRolesHasPermissions(Integer userId);

    //查询受权限数量
    int countPermissionsByModuleId(Integer mid);
    //删除受权限
    int deletePermissionsByModuleId(Integer mid);
}