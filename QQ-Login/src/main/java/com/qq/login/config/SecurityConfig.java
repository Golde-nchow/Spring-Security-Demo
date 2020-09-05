package com.qq.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:56
 * @description: Security配置类
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 表单提交
        http.apply(springSocialConfigurer)
            .and()
            .formLogin()
            // 登录页面
            .loginPage("/login")
            // 成功后默认跳转到index
            .successForwardUrl("/index")
            .and()
            .authorizeRequests()
            // 允许login.html被所有人访问
            .antMatchers("").permitAll()
            // 其他请求需要认证
            .anyRequest().authenticated();

        // 如果不想要表单提交，则使用 http.basic() 实现弹窗登录

        // 关闭 csrf 过滤
        http.csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
