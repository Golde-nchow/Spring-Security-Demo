package sms.oauth2.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Kam-Chou
 * @date: 2020/7/28 22:43
 * @description: 登录控制器
 * @version: 1.0
 */
@Controller
public class LoginController {

    /**
     * 登录页面
     */
    @RequestMapping("login")
    public String loginPage() {
        return "redirect:login.html";
    }

    /**
     * 首页
     */
    @RequestMapping("index")
    public String index() {
       return "redirect:index.html";
    }

}
