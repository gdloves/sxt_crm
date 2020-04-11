//分页查询：刷新功能
function searchUsers() {
    var userName=$("#s_userName").val();
    var phone=$("#s_phone").val();
    var trueName=$("#s_trueName").val();
    //刷新
    $("#dg").datagrid("load",{
        userName:userName,
        phone:phone,
        trueName:trueName
    })
}

//添加功能
//弹出对话框
function openUserAddDialog() {
    //打开对话框
    clearFormData();
    //调用封装好的方法
    openDialog("dlg","用户添加");

}

//关闭对话框
function closeUserDialog() {
    //调用封装好的方法
    closeDialog("dlg");
}

//清除表单
function  clearFormData(){
    $("#userName").val("");
    $("#email").val("");
    $("#trueName").val("");
    $("#phone").val("");
    $("input[name='id']").val("");
}

//保存按钮
function saveOrUpdateUser() {
    //调用封装好的方法
    saveOrUpdateRecode(ctx+"/user/save",ctx+"/user/update","dlg",searchUsers,clearFormData);
}


//更新对话框
function openUserModifyDialog() {
    //判断表格是否有选择记录
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("crm","至少选择一条记录！","error");
        return;
    }

    if(rows.length>1){
        $.messager.alert("crm","不支持批量修改！","error");
        return;
    }

    //符合条件时，填充数据
    $("#fm").form("load",rows[0]);
    //打开对话框
    $("#dlg").dialog("open").dialog("setTitle","修改数据");
}


//删除功能
function deleteUser() {
    //调用封装好的方法
    deleteRecode("dg",ctx+"/user/delete",searchUsers);
}

