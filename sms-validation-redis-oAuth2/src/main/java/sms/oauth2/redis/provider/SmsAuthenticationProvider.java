package sms.oauth2.redis.provider;

import com.cjz.sms.validation.token.SmsAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author User
 * @description
 * @date 2020/8/24 23:42
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * 身份认证逻辑
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 传过来的肯定是 SmsAuthenticationToken
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        // 读取手机号码
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (ObjectUtils.isEmpty(userDetails)) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 第一个是用户信息，第二个是用户权限
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(userDetails);

        return authenticationResult;
    }

    /**
     * 支持哪个 AuthenticationToken，当然是我们自定义的 SmsAuthenticationToken
     * 所以我们只需要判断传过来的东西是否是 SmsAuthenticationToken
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
