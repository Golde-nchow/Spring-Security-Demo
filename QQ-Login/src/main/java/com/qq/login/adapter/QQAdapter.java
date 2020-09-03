package com.qq.login.adapter;

import com.qq.login.entity.QQ;
import com.qq.login.entity.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author User
 * @description QQ适配器
 * @date 2020/9/3 22:25
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试该 api 是否有效，复杂一点可以发送请求
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 设置个性化数据
     */
    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo userInfo = qq.getUserInfo();

        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        // 主页
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
