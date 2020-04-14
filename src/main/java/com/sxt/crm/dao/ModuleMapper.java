package com.sxt.crm.dao;

import com.sxt.base.BaseMapper;
import com.sxt.crm.dto.ModuleDto;
import com.sxt.crm.dto.TreeDto;
import com.sxt.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    //查询所有权限信息
    List<TreeDto> queryAllModules();

    //根据等级和名称查询菜单信息
    Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade,@Param("moduleName") String moduleName);

    //根据url查询
    Module queryModuleByGradeAndUrl(@Param("grade") Integer grade,@Param("url") String url);

    //根据上级id
    Module queryModuleByOptValue(String optValue);

    //下拉框值
    List<Map<String, Object>> queryAllModulesByGrade(Integer grade);

    //根据上级id查询是否存在子菜单
    int countSubModuleByParentId(Integer mid);

    //动态展示菜单列表
    public List<ModuleDto> queryUserHasRoleHasModuleDtos(@Param("userId") Integer userId,@Param("grade") Integer grade,@Param("parentId") Integer parentId);
}