package com.cjz.sms.validation.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kam-Chou
 * @description 默认短信验证码发送
 * @date 2020/8/20 21:49
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 发送验证码
     * @param mobile 手机号码
     * @param code   验证码
     */
    @Override
    public void send(String mobile, String code) {
        logger.info("使用默认的短信验证码向 " + mobile + " 发送验证码：" + code);
    }
}
