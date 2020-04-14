package com.sxt.crm.service;


import com.sxt.base.BaseService;
import com.sxt.crm.dao.ModuleMapper;
import com.sxt.crm.dao.PermissionMapper;
import com.sxt.crm.dto.ModuleDto;
import com.sxt.crm.dto.TreeDto;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    //权限信息
    public List<TreeDto> queryAllModules(){
        return moduleMapper.queryAllModules();
    }

    //查询受权限表中是否有该角色的权限信息
    public List<TreeDto> queryAllModules02(Integer roleId){
        //权限信息
        List<TreeDto> treeDtos=moduleMapper.queryAllModules();
        // 根据角色id 查询角色拥有的菜单id  List<Integer>
        List<Integer> roleHasMids=permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);
        //判断
        if(roleHasMids!=null && roleHasMids.size()>0){
            treeDtos.forEach(treeDto->{
                if(roleHasMids.contains(treeDto.getId())){
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtos;
    }

    //菜单信息添加
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveModule(Module module){
        /**
         * 1.参数校验
         *     模块名-module_name
         *         非空  同一层级下模块名唯一
         *     url
         *         二级菜单  非空  不可重复
         *     上级菜单-parent_id
         *         一级菜单   null
         *         二级|三级菜单 parent_id 非空 必须存在
         *      层级-grade
         *          非空  0|1|2
         *       权限码 optValue
         *          非空  不可重复
         * 2.参数默认值设置
         *     is_valid  create_date update_date
         * 3.执行添加 判断结果
         */
        //模块名-module_name: 非空  同一层级下模块名唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"菜单名不能为空！");
        //层级grade： 非空  0|1|2
        Integer grade=module.getGrade();
        AssertUtil.isTrue(grade==null || !(grade==0 || grade==1 || grade==2),"菜单等级不合法！");
        //根据等级和名称查询菜单信息是否重复
        AssertUtil.isTrue(moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName())!=null,"该层级下菜单重复!");
        //二等级的添加
        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请指定二级菜单url值");
            AssertUtil.isTrue(moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl())!=null,"二级菜单url不可重复!");
        }
        //不是一等级
        if(grade!=0){
            //上级菜单的id:就是主键
            Integer parentId=module.getParentId();
            AssertUtil.isTrue(parentId==null || selectByPrimaryKey(parentId)==null,"请指定上级菜单!");
        }

        //权限码值：非空，唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空!");
        AssertUtil.isTrue(null !=moduleMapper.queryModuleByOptValue(module.getOptValue()),"权限码重复!");

        //设置默认值
        module.setIsValid((byte)1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        //添加
        AssertUtil.isTrue(insertSelective(module)<1,"菜单添加失败!");

    }

    //下拉框信息
    public List<Map<String, Object>> queryAllModulesByGrade(Integer grade) {
        return moduleMapper.queryAllModulesByGrade(grade);
    }

    //更新操作
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module){
        /**
         * 1.参数校验
         *     id 非空 记录存在
         *     模块名-module_name
         *         非空  同一层级下模块名唯一
         *     url
         *         二级菜单  非空  不可重复
         *     上级菜单-parent_id
         *         二级|三级菜单 parent_id 非空 必须存在
         *      层级-grade
         *          非空  0|1|2
         *       权限码 optValue
         *          非空  不可重复
         * 2.参数默认值设置
         *      update_date
         * 3.执行更新 判断结果
         */
        AssertUtil.isTrue(null == module.getId() || null== selectByPrimaryKey(module.getId()),"待更新记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"请指定菜单名称!");
        Integer grade =module.getGrade();
        AssertUtil.isTrue(null== grade|| !(grade==0||grade==1||grade==2),"菜单层级不合法!");
        //查询是否存在修改数据
        Module temp =moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName());
        if(null !=temp){
            //判断菜单名称是否重复
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单已存在!");
        }

        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请指定二级菜单url值");
            temp =moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl());
            if(null !=temp){
                AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下url已存在!");
            }
        }

        if(grade !=0){
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(null==parentId || null==selectByPrimaryKey(parentId),"请指定上级菜单!");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入权限码!");
        //权限码值：非空，唯一
        temp =moduleMapper.queryModuleByOptValue(module.getOptValue());
        if(null !=temp){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"权限码已存在!");
        }
        //设置默认值
        module.setUpdateDate(new Date());
        //更新功能
        AssertUtil.isTrue(updateByPrimaryKeySelective(module)<1,"菜单更新失败!");
    }

    //删除功能
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModuleById(Integer mid){
        //根据id查询菜单信息
        Module temp =selectByPrimaryKey(mid);
        AssertUtil.isTrue(null == mid || null == temp,"待删除记录不存在!");
        /**
         * 如果存在子菜单 不允许删除
         */
        int count = moduleMapper.countSubModuleByParentId(mid);
        AssertUtil.isTrue(count>0,"存在子菜单，不支持删除操作!");

        //  权限表
        count =permissionMapper.countPermissionsByModuleId(mid);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByModuleId(mid)<count,"菜单删除失败!");
        }

        temp.setIsValid((byte) 0);
        //删除功能
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"菜单删除失败!");

    }

    //动态展示菜单列表
    public List<ModuleDto> queryUserHasRoleHasModuleDtos(Integer userId){
        /**
         * 1.查询用户角色分配的一级菜单
         * 2.根据一级菜单查询用户角色分配的二级菜单
         */
        List<ModuleDto> moduleDtos=moduleMapper.queryUserHasRoleHasModuleDtos(userId,0,null);
        if(null !=moduleDtos && moduleDtos.size()>0){
            moduleDtos.forEach(moduleDto -> {
                moduleDto.setSubModules(moduleMapper.queryUserHasRoleHasModuleDtos(userId,1,moduleDto.getId()));
            });
        }
        return moduleDtos;
    }
}
