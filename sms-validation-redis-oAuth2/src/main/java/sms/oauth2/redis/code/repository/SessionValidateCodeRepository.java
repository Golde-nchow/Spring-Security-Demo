package sms.oauth2.redis.code.repository;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import sms.oauth2.redis.code.ValidateCode;
import sms.oauth2.redis.code.ValidateCodeRepository;
import sms.oauth2.redis.code.ValidateCodeType;

/**
 * @author User
 * @description session存储器
 * @date 2020/9/20 15:01
 */
@Component("sessionValidateCodeRepository")
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 保存的验证码
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), code);
    }

    /**
     * 构建验证码放入session时的key
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

    /**
     * 获取 session 中的验证码
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType));
    }

    /**
     * 移除 session 中的验证码
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, codeType));
    }

}
