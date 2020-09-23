package simple.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import simple.jwt.properties.SecurityProperties;

/**
 * @author caojinzhou
 * @description JWT-Token配置
 * @date 2020/9/23 23:05
 */
@Configuration
public class JwtTokenConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(securityProperties.getOauth2Properties().getSigningKey());
        return converter;
    }

}
