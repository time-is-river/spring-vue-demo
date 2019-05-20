package com.casic.demo.service.user;

import com.casic.demo.entity.*;
import com.casic.demo.service.captcha.CaptchaService;
import com.casic.demo.utils.ResultGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.casic.demo.dao.repository.SysUserRepository;

import javax.servlet.http.HttpSession;

/**
 * 用户服务层实现类
 * Created by bekey on 2017/12/20.
 */
@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CaptchaService captchaService;

    @Override
    public SysUser saveUser(SysUser user) {
        return userRepository.save(user);
    }

    @Override
    public SysUser checkLogin(String name, String password) {
        return userRepository.findFirstByNameAndPassword(name, password);
    }

    @Override
    public SysUser currentUser() {
        return ((JwtSysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    @Override
    public RestResult login(LoginRequest loginRequest, HttpSession session) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        try {
            //校验图形验证码
            RestResult restResult = captchaService.validate(loginRequest.getKey(), loginRequest.getImageCode());
            if (restResult == null || restResult.getCode() != ResultCode.SUCCESS.getCode()) {
                return restResult;
            }
            //字段校验
            if (StringUtils.isEmpty(loginRequest.getUserName()) || StringUtils.isEmpty(loginRequest.getPassword())) {
                new RestResult(ResultCode.FAIL.getCode(),"用户名/密码不能为空");
            }
            //此处调用loadUserByUserName
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT",SecurityContextHolder.getContext());
           return new RestResult(ResultCode.SUCCESS.getCode(), "登录成功", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
           //todo 用户的session 管理需要结合前端 重新设计
            /*if(user != null) {
                //储存到session中
                session.setAttribute("user",user);
                return generator.getSuccessResult("登陆成功",user);

            }*/
        } catch (AuthenticationException e) {
            return new RestResult(ResultCode.FAIL.getCode(),"用户名/密码错误");
        }


    }
}