package sms.oauth2.redis.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author User
 * @description 验证码存取接口
 * @date 2020/9/20 14:38
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);

}
