package com.cjz.simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:56
 * @description: Security配置类
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                .loginPage("/login.html")
                // 输入 /login 就会被认为是登录操作
                .loginProcessingUrl("/login");

        http.authorizeRequests()
                // 允许login.html被所有人访问
                .antMatchers("/login.html").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated();

        // 关闭 csrf 过滤
        http.csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
