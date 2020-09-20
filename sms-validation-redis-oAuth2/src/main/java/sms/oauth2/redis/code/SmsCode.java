package sms.oauth2.redis.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信验证码类
 * @author Kamchou
 */
public class SmsCode extends ValidateCode implements Serializable {

    private static final long serialVersionUID = -3203202675038114141L;

    public SmsCode(String code) {
        super(code, 1000);
    }

    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }
}
