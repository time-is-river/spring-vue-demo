package com.casic.demo.service.user;

import com.casic.demo.entity.JwtSysUser;
import com.casic.demo.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.casic.demo.dao.repository.SysUserRepository;

/**
 * 用户服务层实现类
 * Created by bekey on 2017/12/20.
 */
@Service("SysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserRepository userRepository;

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
}