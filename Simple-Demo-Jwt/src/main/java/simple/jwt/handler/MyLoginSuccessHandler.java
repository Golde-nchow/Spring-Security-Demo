package simple.jwt.handler;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 登录成功处理类
 */
@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        logger.info("登录成功");

        // 获取认证信息
        UsernamePasswordAuthenticationToken authRequest = authenticationConverter.convert(request);
        if (authRequest == null) {
            throw new UnapprovedClientAuthenticationException("请求头中无 client 信息");
        }

        // 获取 clientId 和 clientSecret
        String username = authRequest.getName();
        String password = (String) authRequest.getCredentials();
        // 客户端信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);

        // 身份验证
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在：" + username);

        } else if (!clientDetails.getClientSecret().equals(password)) {
            // 如果 secret 不正确，则返回
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配：" + password);
        }

        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), username, clientDetails.getScope(), "password");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        // 最终的带有 accessToken 以及其他信息的 token
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }
}
