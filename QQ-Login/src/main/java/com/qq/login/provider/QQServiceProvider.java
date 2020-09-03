package com.qq.login.provider;

import com.qq.login.entity.BaseQQ;
import com.qq.login.entity.QQ;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author User
 * @description 服务提供商
 * @date 2020/8/28 22:02
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    /**
     * 未授权的时候跳转的页面，获取授权码
     */
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 授权码再获取 access_token
     */
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        super(new OAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQ(accessToken, appId);
    }
}
