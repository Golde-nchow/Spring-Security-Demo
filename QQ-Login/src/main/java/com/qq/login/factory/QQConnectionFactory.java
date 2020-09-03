package com.qq.login.factory;

import com.qq.login.adapter.QQAdapter;
import com.qq.login.entity.QQ;
import com.qq.login.provider.QQServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author User
 * @description QQ连接工厂
 * @date 2020/9/3 22:36
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * QQ连接工厂构造函数
     * @param providerId 是qq还是微信，用于区分提供商
     * @param appId      QQ服务供应商 id
     * @param appSecret  QQ服务供应商密钥
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
