//格式化
function formatterState(value) {
    if(value==0){
        return "暂缓流失";
    }else if(value==1){
        return "确认流失";
    }else {
        return "状态未知";
    }
}

//分页多条件查询
function searchCustomerLoss() {
    $("#dg").datagrid("load",{
        cusNo:$("#s_cusNo").val(),
        cusName:$("#s_cusName").val(),
        state:$("#s_state").combobox("getValue")
    })
}

//格式化
function formatterOp(val,rowData) {
    var state=rowData.state;
    var title=rowData.cusName;
    //打开选项卡
    var href='javascript:openCustomerRepTab("'+title+'","'+rowData.cusNo+'")'
    if(state==0){
        return "<a href='"+href+"'>添加暂缓</a>";
    }
    if(state==1){
        return "<a href='"+href+"'>查看详情</a>"
    }
}

//打开选项卡:跳转页面
function openCustomerRepTab(title,cusNo) {
    window.parent.openTab(title,ctx+"/customer_rep/index?cusNo="+cusNo);
}