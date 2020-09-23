package simple.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author caojinzhou
 * @description token存储配置
 * @date 2020/9/23 22:13
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 存储在 Redis 中
     */
    @Bean
    @ConditionalOnProperty(prefix = "cjz.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 存储在 jwt 中
     * 如果配置文件中不存在 cjz.security.oauth2.storeType = jwt 时，使用下面配置进行存储
     */
    @Bean
    @ConditionalOnProperty(prefix = "cjz.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public TokenStore jwtTokenStore(JwtAccessTokenConverter converter) {
        return new JwtTokenStore(converter);
    }
}
