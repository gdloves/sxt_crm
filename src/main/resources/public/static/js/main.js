//打开选项卡
function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}


//退出操作
function logout() {
    //提示语
    $.messager.confirm("退出","确认退出系统吗？",function (r) {
        if(r){
            //清除cookie
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("trueName");
            //设置定时退出,跳转到登入页面
            setTimeout(function () {
                window.location.href=ctx+"/index";
            },3000);
        }
    })
}



//打开修改密码对话框
function openPasswordModifyDialog() {
    console.log("进来了");
    //打开对话框
    $("#dlg").dialog("open").dialog("setTitle","密码修改");

}



//对话框中的保存按钮
function modifyPassword() {
    console.log("进来了");
    //提交
    $("#fm").form("submit",{
        url:ctx+"/user/updatePassword",
        onSubmint:function () {
            //校验
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            console.log(data.code);
            if(data.code==200){
                //提示语
                $.messager.alert("crm","密码修改成功，系统将在5秒后执行退出操作...","info");
                //清除cookie
                $.removeCookie("userIdStr");
                $.removeCookie("userName");
                $.removeCookie("trueName");
                //设置定时退出,跳转到登入页面
                setTimeout(function () {
                    window.location.href=ctx+"/index";
                },5000)
            }else{

                $.messager.alert("crm",data.msg,"error");
            }
        }
    })
}

//关闭对话框
function closeDialog() {
    console.log("进来了");
    $("#dlg").dialog("close");
}