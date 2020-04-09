function login() {
   var userName=$("input[name='userName']").val();
   var userPwd=$("input[name='password']").val();

   //判断用户名和密码是否为空
    if(isEmpty(userName)){
        alert("用户名不能为空！");
        return;
    }
    if(isEmpty(userPwd)){
        alert("密码不能为空!");
        return;
    }

    //不为空发送ajax请求
    $.ajax({
        type:"post",
        url:ctx+"/user/login",
        data:{
            userName:userName,
            userPwd:userPwd
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $.messager.alert("crm",data.msg,"info");
                var result=data.result;
                console.log(result.trueName);
                //设置cookie
                $.cookie("userIdStr",result.userIdStr);
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                //进入后台系统界面
                window.location.href=ctx+"/main";
            }else{
                $.messager.alert("crm",data.msg,"error");
            }
        }
    })
}