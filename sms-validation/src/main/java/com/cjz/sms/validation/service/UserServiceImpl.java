package com.cjz.sms.validation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author: Kam-Chou
 * @date: 2020/7/29 6:58
 * @description: 加密逻辑
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("执行手机验证码登录逻辑：" + username);
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
}
