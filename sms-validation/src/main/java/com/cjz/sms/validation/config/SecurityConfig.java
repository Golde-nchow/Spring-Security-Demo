package com.cjz.sms.validation.config;

import com.cjz.sms.validation.filter.ValidationCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:56
 * @description: Security配置类
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ValidationCodeFilter validationCodeFilter;

    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 表单提交
        http.addFilterBefore(validationCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
                // 登录页面
                .loginPage("/login")
                // 输入 /login 就会被认为是登录操作，然后使用 UsernameAndPasswordFilter 执行表单登录，并校验逻辑
                // 就是因为这个，导致无法进行验证码判断，TMD
                // 成功后默认跳转到index2
//                .loginProcessingUrl("/authentication/sms")
                .successForwardUrl("/index")
                .and()
            .authorizeRequests()
                // 允许login.html被所有人访问
                .antMatchers("/login*", "/code/sms", "/authentication/sms").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
                .and()
            .csrf().disable()
            // 启用 smsConfig 配置
            .apply(smsAuthenticationSecurityConfig);

        // 如果不想要表单提交，则使用 http.basic() 实现弹窗登录

        // 关闭 csrf 过滤
        http.csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
