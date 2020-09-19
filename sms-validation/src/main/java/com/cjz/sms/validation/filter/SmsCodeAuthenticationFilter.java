package com.cjz.sms.validation.filter;

import com.cjz.sms.validation.token.SmsAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author User
 * @description 短信验证码过滤器，参照 UsernamePasswordAuthenticationFilter
 * @date 2020/8/24 7:20
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private boolean postOnly = true;

    // ~ 构造函数
    // ===================================================================================================

    public SmsCodeAuthenticationFilter() {
        // 当请求是xx时，拦截这个请求
        super(new AntPathRequestMatcher("/authentication/sms", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainMobile(request);
        if (username == null) {
            username = "";
        }
        username = username.trim();
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(username);
        // Allow subclasses to set the "details" com.cjz.oauth2.property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 内部通过 request 请求获取手机号码
     */
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     *
     */
    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 设置手机号码
     */
    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    /**
     * 设置是否只用于 post 请求
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    /**
     * 外部直接获取手机号
     */
    public final String getMobileParameter() {
        return mobileParameter;
    }
}
