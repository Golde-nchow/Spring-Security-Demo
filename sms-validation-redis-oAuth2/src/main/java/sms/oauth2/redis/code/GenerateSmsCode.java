package sms.oauth2.redis.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import sms.oauth2.redis.exception.ValidationCodeException;

import java.util.Random;

/**
 * @author User
 * @description 生成短信验证码类
 * @date 2020/8/16 17:23
 */
@Component
public class GenerateSmsCode {

    /**
     * 验证码存储器，可以使用：
     * sessionValidateCodeRepository
     * redisValidateCodeRepository
     */
    @Autowired
    @Qualifier("redisValidateCodeRepository")
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 生成短信验证码
     * @param length   验证码字符长度
     * @param expireIn 过期时间，秒
     * @return         验证码类 {@link SmsCode}
     */
    public ValidateCode generate(int length, int expireIn) {
        Random random = new Random();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
        }
        return new SmsCode(sRand.toString(), expireIn);
    }

    /**
     * 检验验证码
     * @param request         存储请求信息
     */
    public void validate(ServletWebRequest request) throws ValidationCodeException, ServletRequestBindingException {
        // 获取验证码
        SmsCode smsCode = (SmsCode) validateCodeRepository.get(request, ValidateCodeType.SMS);

        // 获取请求中的验证码
        String code = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        if (StringUtils.isEmpty(code)) {
            throw new ValidationCodeException("验证码不能为空");
        }

        if (ObjectUtils.isEmpty(smsCode)) {
            throw new ValidationCodeException("验证码不存在");
        }

        if (smsCode.isExpired()) {
            throw new ValidationCodeException("验证码已过期");
        }

        if (!smsCode.getCode().equalsIgnoreCase(code)) {
            throw new ValidationCodeException("验证码不匹配");
        }

        // 移除验证码
        validateCodeRepository.remove(request, ValidateCodeType.SMS);
    }

}
