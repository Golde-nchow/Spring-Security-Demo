package simple.jwt.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import simple.jwt.properties.SecurityProperties;

/**
 * 让 SecurityProperties 的配置生效
 * @author Kam-Chou
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PropertiesConfig {

}
