package sms.oauth2.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import sms.oauth2.redis.handler.MyLoginFailureHandler;
import sms.oauth2.redis.handler.MyLoginSuccessHandler;
import sms.oauth2.redis.property.SecurityProperties;

/**
 * @author KamChou
 * @description oauth2资源服务器，获取 access_token。
 * 如果是授权码模式，需要把/oauth/authorize这个地址过滤掉，不然会出现无权限异常。
 * 但是在密码模式当中，则不需要。
 *
 * @date 2020/9/19 19:05
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;

    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;

    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    /**
     * 配置拦截的请求，配置和 SecurityConfig 一致
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

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
                .antMatchers("login*", "/code/sms", "/authentication/sms")
                .permitAll()
                // 其他请求需要认证
                .anyRequest()
                .authenticated();

        // 如果不想要表单提交，则使用 http.basic() 实现弹窗登录

        // 关闭 csrf 过滤
        http.csrf().disable().apply(smsAuthenticationSecurityConfig);;
    }
}
