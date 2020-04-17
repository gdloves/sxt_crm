package com.sxt.crm.service;


import com.sxt.base.BaseService;

import com.sxt.crm.dao.UserMapper;
import com.sxt.crm.dao.UserRoleMapper;
import com.sxt.crm.model.UserModel;
import com.sxt.crm.utils.AssertUtil;
import com.sxt.crm.utils.Md5Util;
import com.sxt.crm.utils.PhoneUtil;
import com.sxt.crm.utils.UserIDBase64;
import com.sxt.crm.vo.User;
import com.sxt.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")//消除所有波浪线
public class UserService extends BaseService<User,Integer> {
   @Autowired
   private UserMapper baseMapper;

   @Autowired
   private UserRoleMapper userRoleMapper;

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

    //用户管理模块：添加功能
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        /**
         * 1.参数校验
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *      isValid 1
         *      createDate   uddateDate
         *      userPwd   123456->md5加密
         * 3.执行添加  判断结果
         */
        //参数校验方法
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        //查询该用户是否存在
        User tempt=baseMapper.queryUserByNP(user.getUserName());
        AssertUtil.isTrue(tempt!=null && (tempt.getIsValid()==1),"用户已存在！");
        //设置默认值
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        //给密码加密
        user.setUserPwd(Md5Util.encode("123456"));
        //钓鱼dao层方法，进行添加操作
        AssertUtil.isTrue(insertHasKey(user)==null,"用户添加失败！");
        //获取主键
        int userId=user.getId();
        /*System.out.println("userId:"+userId);
        System.out.println("RoleIds:"+user.getRoleIds());*/
        /**
         * 用户角色分配
         *    userId
         *    roleIds
         */
        //角色信息添加操作
        relaionUserRole(userId,user.getRoleIds());

    }

    //参数校验方法
    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(email), "请输入邮箱地址!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)), "手机号格式不合法!");
    }

    //封装方法
    private void relaionUserRole(int useId, List<Integer> roleIds) {
        /**
         * 更新：
         * 用户角色分配
         *   原始角色不存在   添加新的角色记录
         *   原始角色存在     添加新的角色记录
         *   原始角色存在     清空所有角色
         *   原始角色存在     移除部分角色
         * 如何进行角色分配???
         *  如果用户原始角色存在  首先清空原始所有角色
         *  添加新的角色记录到用户角色表
         */
        int count = userRoleMapper.countUserRoleByUserId(useId);
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(useId) != count, "用户角色分配失败!");
        }
        if (null != roleIds && roleIds.size() > 0) {
            List<UserRole> userRoles = new ArrayList<UserRole>();
            roleIds.forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(useId);
                userRole.setRoleId(roleId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            });
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles) < userRoles.size(), "用户角色分配失败!");
        }
    }

    //用户管理：更新操作
    //用户管理模块：添加功能
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        /**
         * 1.参数校验
         *      id     非空   唯一
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *      isValid 1
         *      createDate   uddateDate
         *      userPwd   123456->md5加密
         * 3.执行添加  判断结果
         */
        //判断是否存在id
        AssertUtil.isTrue(user.getId()==null || selectByPrimaryKey(user.getId())==null,"更新记录不存在！");
        //参数校验方法
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        //查询该用户是否存在
        User temp=baseMapper.queryUserByNP(user.getUserName());
        if(temp!=null && (temp.getIsValid()==1)){
            //其他用户的用户名相同：不只是与自己用户名相同
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())),"用户已存在！");
        }
        //设置默认值
        user.setUpdateDate(new Date());
        //钓鱼dao层方法，进行添加操作
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户更新失败！");
        //角色更新操作
        relaionUserRole(user.getId(),user.getRoleIds());
    }

    //用户管理：删除功能
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer userId) {

        User user = selectByPrimaryKey(userId);
        System.out.println(user);
        //参数判断
        AssertUtil.isTrue(null == userId || null == user, "待删除记录不存在!");
        //删除角色信息
        int count = userRoleMapper.countUserRoleByUserId(userId);
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count, "用户角色分配失败!");
        }
        //设置有效值
        user.setIsValid(0);
        //不是真正删除
        AssertUtil.isTrue(updateByPrimaryKeySelective(user) < 1, "用户记录删除失败!");

    }


    //分配人
    public List<Map<String,Object>> queryAllCustomerManager(){
        return baseMapper.queryAllCustomerManager();

    }
}
