//初始化
function formatterGrade(grade) {
    if(grade==0){
        return "一级菜单";
    }
    if(grade==1){
        return "二级菜单";
    }
    if(grade==2){
        return "三级菜单";
    }

}

//分页多条件查询
function searchModules() {
    $("#dg").datagrid("load",{
        moduleName:$("#s_moduleName").val(),
        code:$("#s_code").val()
    })
}


//打开对话框
function openModuleAddDialog() {
    //设置下拉框默认值
    $("#parentId").combobox("setValue",$("#pId").val());
    openDialog("dlg","菜单添加");
}

//关闭对话框
function closeModuleDialog() {
    closeDialog("dlg");
}

//清除数据
function  clearFormData(){
    $("#moduleName").val("");
    $("#moduleStyle").val("");
    $("#optValue").val("");
    $("#orders").val("");
    $("input[name='id']").val("");
}

//修改对话框
function openModuleModifyDialog() {
    openModifyDialog("dg","fm","dlg","菜单更新");
}

//保存按钮：添加功能

function saveOrUpdateModule() {
    saveOrUpdateRecode(ctx+"/module/save",ctx+"/module/update","dlg",searchModules,clearFormData);
}


//删除功能
function deleteModule() {
    deleteRecode("dg",ctx+"/module/delete",searchModules);
}