package simple.jwt.enhancer;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author User
 * @description Jwt增强器
 * @date 2020/9/24 22:50
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        // jwt 存储的信息，可以增加自定义属性
        Map<String, Object> params = new HashMap<>(8);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(params);
        return accessToken;
    }
}
