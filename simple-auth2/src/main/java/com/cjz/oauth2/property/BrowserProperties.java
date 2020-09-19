package com.cjz.oauth2.property;

import lombok.Data;

/**
 * 把 cjz.security.browser 的配置封装到指定属性中
 * @author Kam-Chou
 */
@Data
public class BrowserProperties {

    /**
     * 如果没有配置，则使用默认的登录页面
     */
    private String loginPage = "/login.html";

}
