package com.cjz.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author KamChou
 * @description oauth2认证服务器配置，获取 token，只需要加上 EnableAuthorizationServer 注解即可
 * @date 2020/9/19 16:27
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig {



}
