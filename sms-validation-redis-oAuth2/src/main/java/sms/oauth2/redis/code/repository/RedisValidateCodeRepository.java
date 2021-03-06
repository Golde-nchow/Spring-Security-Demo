package sms.oauth2.redis.code.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import sms.oauth2.redis.code.ValidateCode;
import sms.oauth2.redis.code.ValidateCodeRepository;
import sms.oauth2.redis.code.ValidateCodeType;
import sms.oauth2.redis.exception.ValidationCodeException;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author User
 * @description Redis存储容器
 * @date 2020/9/20 15:00
 */
@Component("redisValidateCodeRepository")
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Resource(name = "redisStringTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Bean("redisStringTemplate")
    public RedisTemplate<Object, Object> redisStringTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 保存验证码
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }

    /**
     * 获取 redis 中的验证码
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return (ValidateCode) value;
    }

    /**
     * 移除 redis 中的验证码
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     *
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isEmpty(deviceId)) {
            throw new ValidationCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

}
