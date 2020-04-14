package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.RoleQuery;
import com.sxt.crm.service.RoleService;
import com.sxt.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

//    查询有效的角色
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRole(){
        return roleService.queryAllRole();
    }

    //角色管理页面
    @RequestMapping("index")
    public String index(){
        return "role";
    }

    //分页条件查询角色信息
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryUserParams(RoleQuery roleQuery){
        //调用service层方法
        return roleService.queryByParams(roleQuery);
    }

    //添加功能
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveRole(Role role){
        //调用service层方法
        roleService.saveRole(role);
        return success("添加成功！");
    }

    //更新操作
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        //调用service层方法
        roleService.updateRole(role);
        return success("更新成功！");
    }

    //删除操作
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id){
        //调用service层方法
        roleService.deleteRole(id);
        return success("删除成功！");
    }

    //添加授权信息
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mids,Integer roleId){
        roleService.addGrant(mids,roleId);
        return success("权限添加成功");
    }
}
