package com.sxt.crm.service;


import com.sxt.base.BaseService;

import com.sxt.crm.dao.UserMapper;
import com.sxt.crm.model.UserModel;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.utils.Md5Util;
import com.sxt.crm.utils.UserIDBase64;
import com.sxt.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@SuppressWarnings("all")//消除所有波浪线
public class UserService extends BaseService<User,Integer> {
   @Autowired
   private UserMapper baseMapper;
    //用户登入
    public UserModel login(String userName, String userPwd){
        /**
         * 1.参数校验
         *    用户名  非空
         *    密码  非空
         * 2.根据用户名  查询用户记录
         * 3.校验用户存在性
         *     不存在  -->记录不存在 方法结束
         * 4.用户存在
         *     校验密码
         *       密码错误-->密码不正确  方法结束
         * 5.密码正确
         *     用户登录成功  返回用户相关信息
         */
        //登入操作方法
        checkLoginParams(userName,userPwd);
        //根据用户名查询用户信息
        User user=baseMapper.queryUserByNP(userName);
        //判断是否存在用户
        AssertUtil.isTrue(null==user,"用户不存在！");
//        System.out.println(user.getUserPwd());
//        System.out.println(Md5Util.encode(userPwd));
        //用户存在时，判断密码是否正确
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"密码错误!");
        return buildUserModelInfo(user);
    }
    //
    private UserModel buildUserModelInfo(User user) {
        return  new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }
    //判断用户名和密码是否为空
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空！");

    }

    //修改密码
    @Transactional(propagation = Propagation.REQUIRED)//添加事物
    public void updateUserPwd(Integer userId,String oldPassword,String newPassword,String configPassword){
        //密码校验
        checkParams(userId,oldPassword,newPassword,configPassword);
        //设置用户新密码
        User user=selectByPrimaryKey(userId);
        //给新密码设置加密
        user.setUserPwd(Md5Util.encode(newPassword));
        //修改密码操作，频道影响行数是否大于1
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码更新失败！");
    }
    //修改密码方法校验
    private void checkParams(Integer userId, String oldPassword, String newPassword, String configPassword) {
//        System.out.println("进入校验");
        User user=selectByPrimaryKey(userId);
//        System.out.println(configPassword);
        AssertUtil.isTrue(userId==null || user==null,"用户未登入或不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原密码！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码！");
        AssertUtil.isTrue(StringUtils.isBlank(configPassword),"请输入确认密码！");
        AssertUtil.isTrue(!(newPassword.equals(configPassword)),"新密码不一致！");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原密码错误!");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码不能与旧密码相同！");
    }
}
