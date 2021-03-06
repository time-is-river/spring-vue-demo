package com.casic.demo.dao.repository;

import com.casic.demo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户Dao层
 * 继承JapRepository，可以实现一些默认方法，如save/findAll/findOne/delete/count/exists 等
 */
public interface SysUserRepository extends JpaRepository<SysUser,Integer> {
    /**
     * 按用户名——密码查找
     */
    SysUser findFirstByNameAndPassword(String name, String password);

    /**
     * 根据用户名查找
     */
    SysUser findByName(String name);

}
