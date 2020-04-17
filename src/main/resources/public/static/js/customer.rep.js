//可编辑表格填充数据
$(function () {
    $("#dg").edatagrid({
        //数据填充路径
        url:ctx+"/customer_rep/list?lossId="+$("#lossId").val(),
        saveUrl:ctx+"/customer_rep/save?lossId="+$("#lossId").val(),
        updateUrl:ctx+"/customer_rep/update",
        destroyUrl:ctx+"/customer_rep/delete"
    })
})

//保存功能
function saveCustomerRep() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}

//删除功能
function delCustomerRep() {
    $("#dg").edatagrid("destroyRow");
    $("#dg").edatagrid("load");
}

//终止流失
function updateSaleChanceDevResult() {
    //消息框
    $.messager.confirm("来自crm","确定流失该客户?",function (r01) {
        if(r01){
            //可以输入文本的并且带“确定”和“取消”按钮的消息窗体
            $.messager.prompt("来自crm","请输入客户流失原因",function (r02) {
                if(r02){
                    alert(r02);
                }
            })
        }
    })
}