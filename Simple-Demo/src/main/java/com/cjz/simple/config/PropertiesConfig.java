package com.cjz.simple.config;

import com.cjz.simple.property.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 让 SecurityProperties 的配置生效
 * @author Kam-Chou
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PropertiesConfig {

}
