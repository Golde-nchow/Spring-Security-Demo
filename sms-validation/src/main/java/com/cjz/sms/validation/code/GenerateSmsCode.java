package com.cjz.sms.validation.code;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author User
 * @description 生成短信验证码类
 * @date 2020/8/16 17:23
 */
@Component
public class GenerateSmsCode {

    /**
     * 生成短信验证码
     * @param length   验证码字符长度
     * @param expireIn 过期时间，秒
     * @return         验证码类 {@link com.cjz.sms.validation.code.SmsCode}
     */
    public SmsCode generate(int length, int expireIn) {
        Random random = new Random();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
        }
        return new SmsCode(sRand.toString(), expireIn);
    }

}
