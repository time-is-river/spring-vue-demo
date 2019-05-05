package com.casic.demo.service.captcha;

import com.casic.demo.entity.RestResult;
import com.casic.demo.utils.Captcha;
import com.casic.demo.utils.Constants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author
 * @date 2019/4/26 9:42
 */
@Service
public class CaptchaService {

    private Cache<String, CaptchaCode> cache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .build();

    /**
     * 获取图形验证码
     * @param key
     * @return
     */
    public byte[] generateImage(String key) {
        String code = RandomStringUtils.randomAlphanumeric(4);
        cache.put(key, CaptchaCode.of(code, Duration.of(5, ChronoUnit.MINUTES)));
        byte [] image = Captcha.generateImage(code);
        return image;
    }

    /**
     * 校验code 是否正确
     * @param key
     * @param code
     * @return
     */
    public RestResult<Void> validate(String key, String code) {
        CaptchaCode captchaCode = cache.getIfPresent(key);
        if (captchaCode != null && captchaCode.getExpireTime() != null
                && LocalDateTime.now().isBefore(captchaCode.expireTime)
                && captchaCode.getCode().equals(code)) {
            cache.invalidate(key);
            return new RestResult<>(true, Constants.ReturnCode.SUCCESS.getCode(), "验证通过", null);
        } else {
            return new RestResult<>(false, Constants.ReturnCode.FAILURE.getCode(), "验证失败", null);
        }
    }

    /**
     * 存入缓存的对象
     */
    @Data
    public static class CaptchaCode {
        private String code;
        private LocalDateTime expireTime;

        static CaptchaService.CaptchaCode of(String code, Duration duration) {
            CaptchaService.CaptchaCode captchaCode = new CaptchaService.CaptchaCode();
            captchaCode.setCode(code);
            LocalDateTime now = LocalDateTime.now();
            captchaCode.setExpireTime(now.plus(duration));
            return captchaCode;
        }
    }
}
