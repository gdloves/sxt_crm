
function formatterState(value) {
    /**
     *  0-未分配
     *  1-已分配
     */
    if(value==0){
        return "未分配";
    }else if(value==1){
        return "已分配";
    }else{
        return "未知";
    }
}

function formatterDevResult(value) {
    /**
     * 0-未开发
     * 1-开发中
     * 2-开发成功
     * 3-开发失败
     */
    if(value==0){
        return "未开发";
    }else if(value==1){
        return "开发中";
    }else if(value==2){
        return "开发成功";
    }else if(value==3){
        return "开发失败";
    }else {
        return "未知"
    }

}


//搜索功能：刷新功能
function searchSaleChance() {
    var customerName=$("#s_customerName").val();
    var createMan=$("#s_createMan").val();
    var state=$("#s_state").combobox("getValue");
    $("#dg").datagrid("load",{
        customerName:customerName,
        createMan:createMan,
        state:state
    })
}

//营销管理的添加功能
//打开添加对话框
function openSaleChanceAddDialog() {
    clearFormData();
    $("#dlg").dialog("open").dialog("crm","添加机会数据");
}

//关闭对话框
function closeSaleChanceDialog() {
    $("#dlg").dialog("close");
}

//在打开对话框时清除数据
function clearFormData() {
    $("#customerName").val("");
    $("#chanceSource").val("");
    $("#linkMan").val("");
    $("#linkPhone").val("");
    $("#cgjl").val("");
    $("#overview").val("");
    $("#assignMan").combobox("setValue","");
    $("input[name='id']").val("");
}

//保存功能
function saveOrUpdateSaleChance() {
    //设置路径
    var url=ctx+"/sale_chance/save";
    //判断是否有id值
    if(!(isEmpty($("input[name='id']").val()))){
        url=ctx+"/sale_chance/update";
    }
    //提交表单
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            //校验
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            //判断:是否添加成功
            if(data.code==200){
                //成功：刷新表格，关闭对话框，清空对话框数据
                closeSaleChanceDialog();//关闭对话框
                searchSaleChance();
                clearFormData();
            }else{
                $.messager.alert("crm",data.msg,"error");
            }
        }
    })
}

//修改对话框
function openSaleChanceModifyDialog() {
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
function deleteSaleChance() {
    //判断表格是否有选择记录
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("crm","至少选中一条记录!","error");
        return;
    }

    //符合条件时,弹出
    $.messager.confirm("crm","确定删除选中的记录?",function (r) {
        if(r){
            var ids= "ids=";
            for(var i=0;i<rows.length;i++){
                if(i<rows.length-1){
                    ids=ids+rows[i].id+"&ids=";
                }else {
                    ids=ids+rows[i].id
                }
            }

            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        //成功，刷新表格
                        searchSaleChance();
                    }else{
                        $.messager.alert("crm",data.msg,"error");
                    }
                }
            })

        }
    })
}