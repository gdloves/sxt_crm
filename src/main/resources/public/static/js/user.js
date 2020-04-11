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
    $("#dlg").dialog("open").dialog("setTitle","用户添加");

}

//关闭对话框
function closeUserDialog() {
    $("#dlg").dialog("close");
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
    //设置路径
    var url=ctx+"/user/save";
    //判断是否有id值
    if(!(isEmpty($("input[name='id']").val()))){
        url=ctx+"/user/update";
    }
    //使用form表单发送请求
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            $("#fm").form("validate");
        },
        success:function (data) {
            var data=JSON.parse(data);
            if(data.code==200){
                //成功后关闭对话框，刷新表格，清空表单
                closeUserDialog();
                searchUsers();
                clearFormData();
            }else{
                $.messager.alert("crm",data.msg,"error");
            }
        }
    })
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
    //判断表格是否有选择记录
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("crm","至少选中一条记录!","error");
        return;
    }

    if(rows.length>1){
        $.messager.alert("crm","不支持批量删除！","error");
        return;
    }
    console.log("id:"+rows[0].id);
    //符合条件时,弹出
    $.messager.confirm("crm","确定删除选中的记录?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/user/delete",
                data:{
                    userId:rows[0].id
                },
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        //成功，刷新表格
                        searchUsers();
                    }else{
                        $.messager.alert("crm",data.msg,"error");
                    }
                }
            })
        }
    })
}

