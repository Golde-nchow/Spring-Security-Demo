package sms.oauth2.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import sms.oauth2.redis.code.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author User
 * @description 验证码校验前端控制器
 * @date 2020/8/16 17:15
 */
@RestController
@RequestMapping("code")
public class ValidationCodeController {

    @Autowired
    private GenerateSmsCode generateSmsCode;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Autowired
    @Qualifier("redisValidateCodeRepository")
    private ValidateCodeRepository validateCodeRepository;

    @GetMapping("sms")
    public void getSmsCode(HttpServletRequest request) throws IOException, ServletRequestBindingException {

        // 生成短信验证码
        ValidateCode smsCode = generateSmsCode.generate(6, 120);
        // 把验证码设置到 request 中
        validateCodeRepository.save(new ServletWebRequest(request), smsCode, ValidateCodeType.SMS);
        // 从 request 中取出手机号码
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        // 发送短信验证码
        smsCodeSender.send(mobile, smsCode.getCode());
    }

}
