package com.sxt.crm.controller;

import com.sxt.base.BaseController;
import com.sxt.crm.dto.TreeDto;
import com.sxt.crm.model.ResultInfo;
import com.sxt.crm.query.ModuleQuery;
import com.sxt.crm.service.ModuleService;
import com.sxt.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    //    查询所有权限信息
    @RequestMapping("queryAllModules2")
    @ResponseBody
    public List<TreeDto> queryAllModules(){
        return moduleService.queryAllModules();
    }

    // 根据角色id查询权限信息
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(Integer roleId){
        return moduleService.queryAllModules02(roleId);
    }

    //判断菜单等级
    @RequestMapping("index/{grade}")
    public String index(@PathVariable Integer grade, Integer mid, Model model) {
        System.out.println("grade:"+grade);
        model.addAttribute("mid", mid);
        if (grade == 1) {
            return "module_1";
        } else if (grade == 2) {
            return "module_2";
        } else if (grade == 3) {
            return "module_3";
        } else {
            return "";
        }
    }

    //分页多条件查询
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModule(ModuleQuery moduleQuery){
        return moduleService.queryByParams(moduleQuery);
    }

    //菜单添加功能
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveModule(Module module){
        moduleService.saveModule(module);
        return success("添加成功！");
    }

    //下拉框默认值
    @RequestMapping("queryAllModulesByGrade")
    @ResponseBody
    public List<Map<String,Object>> queryAllModulesByGrade(Integer grade){
        return moduleService.queryAllModulesByGrade(grade);
    }

    //修改操作
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("菜单更新成功");
    }

    //删除功能
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModuleById(id);
        return success("菜单删除成功");
    }
}
