package sms.oauth2.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sms.oauth2.redis.code.DefaultSmsCodeSender;
import sms.oauth2.redis.code.SmsCodeSender;

/**
 * @author Kam-Chou
 * @description 验证码 bean 配置
 * @date 2020/8/20 21:56
 */
@Configuration
public class ValidationCodeBeanConfig {

    /**
     * 短信验证码发送
     * 如果没有自定义的短信发送器，则使用默认的短信发送器
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
