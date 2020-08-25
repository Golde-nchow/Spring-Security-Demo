package com.cjz.sms.validation.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author User
 * @description 验证码异常
 * @date 2020/8/16 18:50
 */
public class ValidationCodeException extends AuthenticationException {

    public ValidationCodeException(String detail) {
        super(detail);
    }
}
