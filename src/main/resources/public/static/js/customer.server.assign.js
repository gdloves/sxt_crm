//分页多条件查询
function searchCustomerServeByParams() {
    $("#dg").datagrid("load",{
        customer:$("#s_customer").val(),
        type:$("#s_serveType").combobox("getValue")
    })
}

//分配对话框打开
function openAssignDialog() {
    //判断是否选中表格
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("crm","至少选中一条分配记录!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("crm","暂不支持批量分配!","error");
        return;
    }

    //填充数据
    $('#fm').form("load",rows[0]);
    //打开对话框
    openDialog("dlg","服务分配");
}


//保存按钮：更新功能
function addAssign() {
    //ajax请求
    $.ajax({
        type:"post",
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        data:{
            assigner:$("#s_assigner").combobox("getValue"),
            state:"fw_002",
            id:$("input[name='id']").val()
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                closeDialog("dlg");
                searchCustomerServeByParams();
            }else {
                $.messager.alert("crm",data.msg,"error");
            }
        }
    })
}