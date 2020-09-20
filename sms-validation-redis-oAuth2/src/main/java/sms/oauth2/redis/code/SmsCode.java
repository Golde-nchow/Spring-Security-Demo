package sms.oauth2.redis.code;

import java.time.LocalDateTime;

/**
 * 短信验证码类
 * @author Kamchou
 */
public class SmsCode extends ValidateCode {

    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }
}
