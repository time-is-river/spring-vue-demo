package com.casic.demo.service.security;

import com.casic.demo.dao.repository.SysUserRepository;
import com.casic.demo.entity.JwtSysUser;
import com.casic.demo.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2019/1/16 16:27
 */
@Component
public class JwtSysUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtSysUser jwtSysUser = new JwtSysUser();
        jwtSysUser.setUsername(username);
        SysUser sysUser = sysUserRepository.findByName(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用戶不存在");
        }
        jwtSysUser.setPassword(sysUser.getPassword());
        jwtSysUser.setUser(sysUser);
        return jwtSysUser;
    }
}
