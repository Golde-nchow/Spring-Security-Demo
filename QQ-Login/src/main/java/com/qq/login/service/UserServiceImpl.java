package com.qq.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:58
 * @description: 账号密码类型的登录逻辑
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("执行登录逻辑");

        // 该逻辑先写死
        String nameFromDataBase = "admin";
        if (!nameFromDataBase.equals(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        String passwordFromDataBase = passwordEncoder.encode("123");
        // 设置用户名、密码、角色
        // true: 可用
        // true: 没过期
        // true: 凭证没过期
        // true: 账号没被锁定
        return new User(username
                , passwordFromDataBase
                , true, true, true, true
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin, normal"));
    }

    /**
     * 用于第三方登录的校验逻辑
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        System.out.println("执行第三方登录逻辑: " + userId);

        // 去数据库查询
        String passwordFromDataBase = passwordEncoder.encode("123");
        // 设置用户id、密码、角色
        // true: 可用
        // true: 没过期
        // true: 凭证没过期
        // true: 账号没被锁定
        return new SocialUser(userId, passwordFromDataBase,
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin, normal"));
    }
}
