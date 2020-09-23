package simple.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import simple.jwt.handler.MyLoginFailureHandler;
import simple.jwt.handler.MyLoginSuccessHandler;
import simple.jwt.properties.SecurityProperties;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:56
 * @description: Security配置类
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;

    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 获取模块抽取出来的，自定义的登录页面
        String loginPage = securityProperties.getBrowser().getLoginPage();

        // 表单提交
        http.formLogin()
            // 登录页面
            .loginPage(loginPage)
            // 输入 /login 就会被认为是登录操作，然后使用 UsernameAndPasswordFilter 执行表单登录，并校验逻辑
            // 成功后默认跳转到index2
            .loginProcessingUrl("/login")
            .successForwardUrl("/index")
            // 添加自定义的登录成功处理
            .successHandler(myLoginSuccessHandler)
            // 添加自定义的登录失败处理
            .failureHandler(myLoginFailureHandler)
            .and()
            .authorizeRequests()
            // 允许login.html被所有人访问
            .antMatchers(loginPage)
            .permitAll()
            // 其他请求需要认证
            .anyRequest()
            .authenticated();

        // 如果不想要表单提交，则使用 http.basic() 实现弹窗登录

        // 关闭 csrf 过滤
        http.csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
