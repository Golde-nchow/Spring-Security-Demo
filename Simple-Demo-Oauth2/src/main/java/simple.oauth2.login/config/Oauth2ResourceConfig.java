package simple.oauth2.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author KamChou
 * @description oauth2资源服务器，获取 access_token。
 * 如果是授权码模式，需要把/oauth/authorize这个地址过滤掉，不然会出现无权限异常。
 * 但是在密码模式当中，则不需要。
 *
 * @date 2020/9/19 19:05
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceConfig {
}
