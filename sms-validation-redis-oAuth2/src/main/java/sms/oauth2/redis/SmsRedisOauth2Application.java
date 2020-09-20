package sms.oauth2.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author KamChou
 * @description oauth2，短信登录，将验证码存储到 redis
 * @date 2020/9/20 13:55
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SmsRedisOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SmsRedisOauth2Application.class, args);
    }

}
