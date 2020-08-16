package com.cjz.image.validation.filter;

import com.cjz.image.validation.code.GenerateImageCode;
import com.cjz.image.validation.exception.ValidationCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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
public class ValidationCodeFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private GenerateImageCode generateImageCode;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String loginUrl = "/authentication";
        String loginMethod = "POST";
        StringBuffer requestURL = request.getRequestURL();
        String method = request.getMethod();

        // 如果是登录请求，则检验
        if (loginUrl.contentEquals(requestURL) && loginMethod.equalsIgnoreCase(method)) {
            try {
                generateImageCode.validate(sessionStrategy, new ServletWebRequest(request));
            } catch (ValidationCodeException e) {

                // 执行登录失败逻辑
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }

        // 如果是其他请求，则放行
        filterChain.doFilter(request, response);

    }
}
