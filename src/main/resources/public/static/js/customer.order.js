//初始化
function formatterState(value) {
    if(value==0){
        return "未支付";
    }else if(value==1){
        return  "已支付";
    }else{
        return "未知"
    }
}

//表格分页多条件查询
function searchOrders() {
    $("#dg").datagrid("load",{
        orderNo:$("#s_orderNo").val(),
        state:$("#s_state").combobox("getValue")
    })
}

//操作格式化
function formatterOp() {
    //打开对话框
    var href="javascript:openOrderDetailDialog()";
    return "<a href='"+href+"'>详情查看</a>";
}

//打开对话框
function openOrderDetailDialog() {
    //判断表格是否选中
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待查看的订单数据!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量查看!","error");
        return;
    }

    //填充form表单数据
    /*$.ajax({
        type:"post",
        url:ctx+"/order/queryOrderDetailByOrderId",
        data:{
            orderId: rows[0].id
        },
        dataType:"json",
        success:function (data) {
            $("#fm").form("load",data);
        }
    });*/
    $("#fm").form("load",ctx+"/order/queryOrderDetailByOrderId?orderId="+rows[0].id);
    //表格数据填充
    $("#dg02").datagrid("load",{
        orderId:rows[0].id
    })
    //打开对话框
    openDialog("dlg","订单详情");
}

//详情查看对话框中的分页查询
function searchOrderDetails() {
    $("#dg02").datagrid("load",{
        orderId:$("#dg").datagrid("getSelections")[0].id,
        goodsName:$("#s_goodsName").val(),
        type:$("#s_price").combobox("getValue")
    })
}