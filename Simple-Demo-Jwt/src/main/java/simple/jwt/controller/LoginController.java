package simple.jwt.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import simple.jwt.properties.SecurityProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author: Kam-Chou
 * @date: 2020/7/28 22:43
 * @description: 登录控制器
 * @version: 1.0
 */
@Controller
public class LoginController {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 登录页面
     */
    @RequestMapping("login")
    public String loginPage() {
        return "redirect:login.html";
    }

    /**
     * 获取用户信息
     */
    @ResponseBody
    @RequestMapping("me")
    public Object getMe(Authentication authentication) {
        // 获取token
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String token = details.getTokenValue();
        // 密钥
        String signingKey = securityProperties.getOauth2Properties().getSigningKey();
        // 返回解析后的信息
        return Jwts.parser()
                .setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
    }

    /**
     * 首页
     */
    @RequestMapping("index")
    public String index() {
       return "redirect:index.html";
    }

    /**
     * 首页2
     */
    @RequestMapping("index2")
    public String index2() {
        return "redirect:mySuccessPage.html";
    }
}
