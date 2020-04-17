//分页多条件查询
function searchCustomerServeByParams() {
    $("#dg").datagrid("load",{
        customer:$("#s_customer").val(),
        type:$("#s_serveType").combobox("getValue")
    })
}

//处理对话框打开
function openFeedBackDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待处理的数据!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量处理!","error");
        return;
    }

    //填充数据
    $('#fm').form("load",rows[0]);
    //打开对话框
    openDialog("dlg","服务处理");
}


//处理按钮功能
function doFeedBack() {
    $.ajax({
        type:"post",
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        data:{
            state:"fw_004",
            id:$("input[name='id']").val(),
            serviceProceResult:$("#serviceProceResult").val(),
            myd:$("#myd").combobox("getValue")
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                closeDialog("dlg");
                searchCustomerServeByParams();
            }
        }
    })
}
