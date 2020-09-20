package simple.oauth2.login.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import simple.oauth2.login.property.SecurityProperties;

/**
 * 让 SecurityProperties 的配置生效
 * @author Kam-Chou
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PropertiesConfig {

}
