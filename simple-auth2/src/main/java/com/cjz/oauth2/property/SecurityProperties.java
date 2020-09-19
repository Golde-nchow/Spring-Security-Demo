package com.cjz.oauth2.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取 application.properties 配置文件当中的 cjz.security 开头的配置
 * 若是 cjz.security.browser，则封装到 BrowserProperties 类中.
 * @author Kam-Chou
 */
@Data
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "cjz.security")
public class SecurityProperties {

    /**
     * 封装 cjz.security.browser.loginPage 的值
     * 若属性变量名为 browserProperties，则改为 cjz.security.browser-properties.loginPage
     * 记得！！记得！！记得！！
     */
    private BrowserProperties browser = new BrowserProperties();

}
