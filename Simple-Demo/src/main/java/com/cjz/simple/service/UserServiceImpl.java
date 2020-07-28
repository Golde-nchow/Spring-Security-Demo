package com.cjz.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        System.out.println("执行登录逻辑");

        // 该逻辑先写死
        String nameFromDataBase = "admin";
        if (!nameFromDataBase.equals(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        String passwordFromDataBase = passwordEncoder.encode("123");
        // 设置用户名、密码、角色
        return new User(username
                , passwordFromDataBase
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin, normal"));
    }
}
