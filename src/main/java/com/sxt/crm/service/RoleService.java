package com.sxt.crm.service;

import com.sxt.base.BaseService;
import com.sxt.crm.dao.ModuleMapper;
import com.sxt.crm.dao.PermissionMapper;
import com.sxt.crm.dao.RoleMapper;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.vo.Permission;
import com.sxt.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;
    //用户管理：角色信息
    public List<Map<String,Object>> queryAllRole(){
        return roleMapper.queryAllRoles();
    }

    //角色管理：角色添
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null !=temp,"该角色已存在!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(role)<1,"角色记录添加失败!");
    }

    //角色管理：角色更新
    @Transactional(propagation = Propagation.REQUIRED)
    public void  updateRole(Role role){
        AssertUtil.isTrue(null==role.getId()||null==selectByPrimaryKey(role.getId()),"待修改的记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(role.getId())),"该角色已存在!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"角色记录更新失败!");
    }

    //角色管理：角色删除
    public void deleteRole(Integer roleId){
        Role temp =selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待删除的记录不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"角色记录删除失败!");
    }

    //角色管理：添加授权信息
    public void addGrant(Integer[] mids, Integer roleId) {
        /**
         * 核心表-t_permission  t_role(校验角色存在)
         *   如果角色存在原始权限  删除角色原始权限
         *     然后添加角色新的权限 批量添加权限记录到t_permission
         */
        Role temp =selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待授权的角色不存在!");
        int count = permissionMapper.countPermissionByRoleId(roleId);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByRoleId(roleId)<count,"权限分配失败!");
        }
        if(null !=mids && mids.length>0){
            List<Permission> permissions=new ArrayList<Permission>();
            for (Integer mid : mids) {
                Permission permission=new Permission();
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }

            //批量添加受权限信息
            permissionMapper.insertBatch(permissions);
        }

    }
}
