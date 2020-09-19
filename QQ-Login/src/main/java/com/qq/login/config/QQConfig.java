package com.qq.login.config;

import com.qq.login.factory.QQConnectionFactory;
import com.qq.login.holder.CurrentUserHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author User
 * @description QQ 登录配置文件
 * @date 2020/9/5 18:05
 */
@Configuration
public class QQConfig extends SocialConfigurerAdapter {

    @Value("${social.qq.providerId}")
    private String providerId;

    @Value("${social.qq.appId}")
    private String appId;

    @Value("${social.qq.appSecret}")
    private String appSecret;

    /**
     * 添加连接工厂
     * 由于没有 SocialAutoConfigurerAdapter，所以只能自己生成该方法
     */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new CurrentUserHolder();
    }

    /**
     * 由于没有 SocialAutoConfigurerAdapter，所以只能自己生成该方法
     */
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(providerId, appId, appSecret);
    }

}
