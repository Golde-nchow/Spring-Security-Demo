package com.cjz.remember.me.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:56
 * @description: Security配置类
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 表单提交
        http.formLogin()
                // 登录页面
                .loginPage("/login")
                // 输入 /login 就会被认为是登录操作，然后使用 UsernameAndPasswordFilter 执行表单登录，并校验逻辑
                // 成功后默认跳转到index2
                .loginProcessingUrl("/authentication")
                .successForwardUrl("/index")
            .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(persistentTokenRepository())
                // 5分钟
                .tokenValiditySeconds(300)
            .and()
                .authorizeRequests()
                // 允许login.html被所有人访问
                .antMatchers("/login*").permitAll()
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

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        // 项目启动的时候创建这个表，用于存放token，创建一次即可
        // tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

}
