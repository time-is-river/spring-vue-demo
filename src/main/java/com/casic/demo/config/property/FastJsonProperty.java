package com.casic.demo.config.property;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author
 * @date 2019/1/14 15:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "fastjson")
public class FastJsonProperty {
    private Charset charset;
    private String dateFormat;
    private SerializerFeature[] serializerFeatures;
}
