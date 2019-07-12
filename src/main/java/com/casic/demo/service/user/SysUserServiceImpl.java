package com.casic.demo.service.user;

import com.casic.demo.entity.*;
import com.casic.demo.service.captcha.CaptchaService;
import com.casic.demo.service.security.JwtSysUserDetailsServiceImpl;
import com.casic.demo.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.casic.demo.dao.repository.SysUserRepository;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 用户服务层实现类
 * Created by bekey on 2017/12/20.
 */
@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {
    private Logger logger = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    SysUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private JwtSysUserDetailsServiceImpl userDetailsService;

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
        final Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
        logger.info("用户登录请求：", loginRequest);
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
            String token = TokenUtils.generateToken(userDetails);
            HashMap<String,String> data = new HashMap(6);
            data.put("token", token);
            data.put("userName", loginRequest.getUserName());
            logger.info("用户登录返回：登录成功");
            return new RestResult(ResultCode.SUCCESS.getCode(), "登录成功", data);
            /*if(user != null) {
                //储存到session中
                session.setAttribute("user",user);
                return generator.getSuccessResult("登陆成功",user);

            }*/
        } catch (AuthenticationException e) {
            logger.info("用户登录返回：用户名/密码错误");
            return new RestResult(ResultCode.FAIL.getCode(),"用户名/密码错误");
        }


    }
}