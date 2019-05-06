package com.casic.demo.entity;

import lombok.Data;

/**
 * @author
 * @date 2019/5/6 15:53
 */
@Data
public class LoginRequest {
    private String userName;
    private String password;
    private String key;
    private String imageCode;
}
