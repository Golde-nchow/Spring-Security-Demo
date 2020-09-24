package simple.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import simple.jwt.enhancer.JwtTokenEnhancer;
import simple.jwt.properties.SecurityProperties;

/**
 * @author caojinzhou
 * @description JWT-Token配置
 * @date 2020/9/23 23:05
 */
@Configuration
@ConditionalOnProperty(prefix = "cjz.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(securityProperties.getOauth2Properties().getSigningKey());
        return converter;
    }

    /**
     * jwt 增强器，为jwt增加额外的信息
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JwtTokenEnhancer();
    }

}
