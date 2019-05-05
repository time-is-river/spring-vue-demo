package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.service.captcha.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date 2019/4/26 13:35
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;


    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] image(String key) {
        return captchaService.generateImage(key);
    }

    @GetMapping("/check")
    public RestResult<Void> check(String key, String code) {
        return captchaService.validate(key, code);
    }

}
