//分页条件查询
function searchRoles() {
    $("#dg").datagrid("load",{
        roleName:$("#s_roleName").val()
    })
}

//打开对话框：添加
function openRoleAddDialog() {
    clearFormData();
    openDialog("dlg","角色添加");
}

//关闭对话框
function closeRoleDialog() {
    closeDialog("dlg");
}

//清除数据
function  clearFormData(){
    $("#roleName").val("");
    $("#roleRemark").val("");
    $("input[name='id']").val("");
}

//添加功能和更新功能
function saveOrUpdateRole() {
    saveOrUpdateRecode(ctx+"/role/save",ctx+"/role/update","dlg",searchRoles,clearFormData);
}

//更新操作对话框
function openRoleModifyDialog() {
    openModifyDialog("dg","fm","dlg","角色更新");
}


//删除功能
function deleteRole() {
    deleteRecode("dg",ctx+"/role/delete",searchRoles);
}


//权限对话框打开
var zTreeObj;
var roleId;
function openAddModuleDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待授权的角色!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量授权!","error");
        return;
    }

    roleId=rows[0].id;

    $.ajax({
        url:ctx+"/module/queryAllModules",
        type:"post",
        data:{
            roleId:roleId
        },
        dataType:"json",
        success:function (data) {
            //zTree
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                check: {//复选框
                    enable: true
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
            openDialog("module","授权");
        }
    });

}


//添加授权信息
function zTreeOnCheck(event, treeId, treeNode) {
    /**
     * 获取ztree中所有选中的节点
     */
    var nodes=zTreeObj.getCheckedNodes(true);
    console.log(nodes);
    var mids="mids=";
    for(var i=0;i<nodes.length;i++){
        if(i<nodes.length-1){
            mids=mids+nodes[i].id+"&mids=";
        }else{
            mids=mids+nodes[i].id;
        }
    }
    console.log(mids);
    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:mids+"&roleId="+roleId,
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    })
};