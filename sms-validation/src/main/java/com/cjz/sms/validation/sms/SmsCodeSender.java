package com.cjz.sms.validation.sms;

/**
 * @author Kam-chou
 * @description 短信验证码
 * @date 2020/8/20 21:47
 */
public interface SmsCodeSender {

    /**
     * 发送验证码
     * @param mobile 手机号码
     * @param code   验证码
     */
    void send(String mobile, String code);

}
