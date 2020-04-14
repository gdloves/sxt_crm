package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.PermissionMapper;
import com.sxt.crm.vo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Autowired
    private PermissionMapper permissionMapper;
    //根据用户id查询受权限信息
    public List<String> queryUserHasRolesHasPermissions(Integer userId) {
        return permissionMapper.queryUserHasRolesHasPermissions(userId);
    }
}
