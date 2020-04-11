//格式化：操作
function formatterOp(value,rowData) {//rowData正行数据
    var deResult=rowData.devResult;//devResult开发状态值
    console.log(rowData);
    var href="javascript:openCusDevPlanDialog()";//操作方法：打开一个对话框
    if(deResult==2 || deResult==3){
        return "<a href='"+href+"'>查看详情</a>";
    }else{
        return "<a href='"+href+"'>开发</a>";
    }
    
}

/*对话框*/
function openCusDevPlanDialog() {
    console.log("进来了");
    //获取选中列
    var recode=$("#dg").datagrid("getSelections")[0];
    //填充数据：使用form表单
    $("#fm").form("load",recode);


    //可编辑表格的数据和工具栏的功能
    //开发成功和失败没有工具栏功能
    if(recode.devResult==2 || recode.devResult==3){
        //表格工具禁用
        $("#toolbar").css("display","none");
        $("#dg02").edatagrid({
            url:ctx+"/cus_dev_plan/list?sid="+recode.id,
            saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+recode.id,
            updateUrl:ctx+"/cus_dev_plan/update",
            destroyUrl:ctx+"/cus_dev_plan/delete"
        })
        //禁用
        $("#dg02").edatagrid("disableEditing");
    }else{
        $("#toolbar").show();
        $("#dg02").edatagrid({
            toolbar:"#toolbar",
            url:ctx+"/cus_dev_plan/list?sid="+recode.id,
            saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+recode.id,
            updateUrl:ctx+"/cus_dev_plan/update",
            destroyUrl:ctx+"/cus_dev_plan/delete"
        });

    }
    //打开对话框
    $("#dlg").dialog("open").dialog("setTitle","开发计划项展示");
    console.log($("#devResult").val());
}

//保存功能
function saveCusDevPlan() {
    //使用可编辑表格的保存方法
    $("#dg02").edatagrid("saveRow");
    //刷新表格
    $("#dg02").edatagrid("load");
}

//删除功能
/*function delCusDevPlan() {
    $("#dg02").edatagrid("destroyRow");
    //刷新表格
    $("#dg02").edatagrid("load");
}*/
function delCusDevPlan() {
    var rows=$("#dg02").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","至少选中一条数据!","error");
        return;
    }

    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量删除操作!","error");
        return;
    }

    $.messager.confirm("crm","确认删除该条记录吗？",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/cus_dev_plan/delete",
                data:{id:rows[0].id},
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        $("#dg02").edatagrid("load");//刷新数据
                    }else{
                        $.messager.alert("crm",data.msg,"error");
                    }
                }
            })
        }
    })

}


function updateSaleChanceDevResult(status) {
    $.ajax({
        type:"post",
        url:ctx+"/sale_chance/updateDevResult",
        data:{
            devResult:status,
            sid:$("#dg").datagrid("getSelections")[0].id
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $("#dg").edatagrid("load");
                /**
                 * 1.更新营销机会表格数据
                 * 2.隐藏开发计划项数据 工具栏
                 * 3.计划项表格数据不可编辑
                 */
                $("#dg02").edatagrid("disableEditing");
            }else{
                $.messager.alert("crm",data.msg,"error");
            }
        }
    })
}