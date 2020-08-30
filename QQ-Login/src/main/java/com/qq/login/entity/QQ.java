package com.qq.login.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author User
 * @description QQ信息实现类，qq文档 https://wiki.connect.qq.com/get_user_info
 * @date 2020/8/28 21:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QQ extends AbstractOAuth2ApiBinding implements BaseQQ {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 申请QQ登录成功后，分配给应用的appid
     */
    private String oauth_consumer_key;

    /**
     * 用户的ID，与QQ号码一一对应。
     * 获取：https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN
     */
    private String openId;

    /**
     * 获取 openId
     */
    private final String GET_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%1$s";

    /**
     * 获取 qq用户信息
     */
    private final String GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%1$s&openid=%2$s";

    private RestTemplate restTemplate = getRestTemplate();

    public QQ(String accessToken, String oauth_consumer_key) {
        // 默认把 access_token 放到请求头中，但是 qq 文档要求放到 url 中.
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.oauth_consumer_key = oauth_consumer_key;
        this.openId = getOpenId(accessToken);
    }

    /**
     * 获取 QQ 用户信息
     */
    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(GET_USER_INFO_URL, oauth_consumer_key, openId);
        String result = restTemplate.getForObject(url, String.class);
        logger.info("qq 用户信息返回结果：{}", result);
        // 返回结果
        return JSONObject.toJavaObject((JSON) JSON.toJSON(result), QQUserInfo.class);
    }

    /**
     * 获取 openid
     * @param accessToken 令牌
     * @return            openId
     */
    private String getOpenId(String accessToken) {
        String url = String.format(GET_OPEN_ID_URL, accessToken);
        String result = restTemplate.getForObject(url, String.class);
        logger.info("openId 接口返回结果：{}", result);
        /*
         * 处理结果
         * callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} )
         */
        Map resultMap = JSONObject.parseObject(result, Map.class);

        /*
         * {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"}
         */
        Map<String, String> callBack = (Map<String, String>) resultMap.get("callback");

        // 最终获取 openid
        return callBack.get("openid");
    }
}
