//提交功能：保存按钮
function saveCustomerServe() {
    $("#fm").form('submit',{
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        onSubmit:function (param) {
            //从cookie获取真实名称
            param.createPeople=$.cookie("trueName");
            return  $("#fm").form("validate");
        },
        success:function (data) {
            var data=JSON.parse(data);
            if(data.code==200){
                $.messager.alert("crm","服务创建成功");
                clearFormData();
            }else {
                $.messager.alert("crm",data.msg);
            }
        }
    })
}

//清除数据
function clearFormData() {
    $("input").val("");
    $("#serveType").combobox("setValue","");
}