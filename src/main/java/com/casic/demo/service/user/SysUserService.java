package com.casic.demo.service.user;

import com.casic.demo.entity.LoginRequest;
import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.SysUser;

import javax.servlet.http.HttpSession;

/**
 * 用户服务层接口
 * Created by bekey on 2017/12/20.
 */
public interface SysUserService {
    /**
     * 注册用户
     * @param user
     * @return 注册成功将用户信息返回，否则返回null
     */
    SysUser saveUser(SysUser user);

    /**
     * 检查用户名密码是否正确
     * @param name 用户名
     * @param password 密码
     * @return 验证通过则将用户信息返回，否则返回null
     */
    SysUser checkLogin(String name,String password);

    /**
     * 获取当前用户信息
     */
    SysUser currentUser();

    /**
     * 用户网页登录
     * @param loginRequest
     * @param httpSession
     * @return
     */
    RestResult login(LoginRequest loginRequest, HttpSession httpSession);
}

