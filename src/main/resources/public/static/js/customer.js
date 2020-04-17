//分页多条件查询openCustomerAddDialog
function searchCustomersByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#name").val(),
        cusNo:$("#khno").val(),
        myd:$("#myd").combobox("getValue"),
        level:$("#level").combobox("getValue")
    })
}

//添加对话框打开
function openCustomerAddDialog() {
    clearFormData();
    openDialog("dlg","客户添加");
}

//关闭对话框
function closeCustomerDialog() {
    closeDialog("dlg");
}

//打开修改对话框
function openCustomerModifyDialog() {
    openModifyDialog("dg","fm","dlg","客户更新");
}

//清除数据
function clearFormData() {
    $("#names").val("");
    $("#area").val("");
    $("#cusManager").val("");
    $("#level").val("");
    $("#xyd").val("");
    $("#address").val("");
    $("#postCode").val("");
    $("#phone").val("");
    $("#fax").val("");
    $("#webSite").val("");
    $("#fr").val("");
    $("#zczj").val("");
    $("#nyye").val("");
    $("#khyh").val("");
    $("#khzh").val("");
    $("#gsdjh").val("");
    $("#dsdjh").val("");

}

//保存按钮：添加和更新操作
function saveOrUpdateCustomer() {
    saveOrUpdateRecode(ctx+"/customer/save",ctx+"/customer/update","dlg",searchCustomersByParams,clearFormData)
}

//删除功能
function deleteCustomer() {
    deleteRecode("dg",ctx+"/customer/delete",searchCustomersByParams);
}

//订单查看选项卡
function openShowOrderTab() {
    //判断表格是否选中
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("crm","至少选中一条记录！","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("crm","暂不支持批量查看！","error");
        return;
    }
    //打开选项卡
    window.parent.openTab(rows[0].name+"_订单展示",ctx+"/customer/order_info?cid="+rows[0].id);
}