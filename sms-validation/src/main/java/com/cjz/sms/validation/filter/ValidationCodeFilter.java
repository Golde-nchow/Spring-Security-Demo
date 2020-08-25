package com.cjz.sms.validation.filter;

import com.cjz.sms.validation.code.GenerateSmsCode;
import com.cjz.sms.validation.exception.ValidationCodeException;
import com.cjz.sms.validation.handler.MyLoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author User
 * @description 验证码过滤器
 * @date 2020/8/16 18:40
 */
@Component
public class ValidationCodeFilter extends OncePerRequestFilter {

    @Autowired
    private MyLoginFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private GenerateSmsCode generateImageCode;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String loginUrl = "/authentication/mobile";
        String loginMethod = "POST";
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // 如果是登录请求，则检验
        if (loginUrl.contentEquals(requestURI) && loginMethod.equalsIgnoreCase(method)) {
            try {
                generateImageCode.validate(sessionStrategy, new ServletWebRequest(request));
            } catch (ValidationCodeException e) {

                System.out.println("报错：" + e.getMessage());
                // 执行登录失败逻辑
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                // 避免继续往下走
                return;
            }
        }

        // 如果是其他请求，则放行
        filterChain.doFilter(request, response);
    }
}
