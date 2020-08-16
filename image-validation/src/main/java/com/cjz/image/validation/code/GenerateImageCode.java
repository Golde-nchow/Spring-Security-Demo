package com.cjz.image.validation.code;

import com.cjz.image.validation.controller.ValidationCodeController;
import com.cjz.image.validation.exception.ValidationCodeException;
import lombok.Data;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author User
 * @description 生成图形验证码类
 * @date 2020/8/16 17:23
 */
@Data
@Component
public class GenerateImageCode {

    /**
     * 生成图形验证码
     * @param width    宽
     * @param height   高
     * @param length   验证码字符长度
     * @param expireIn 过期时间，秒
     * @return         验证码类 {@link com.cjz.image.validation.code.ImageCode}
     */
    public ImageCode generate(int width, int height, int length, int expireIn) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, expireIn);
    }

    /**
     * 检验验证码
     * @param sessionStrategy 存储验证码的 session 策略类
     * @param request         存储请求信息
     */
    public void validate(SessionStrategy sessionStrategy, ServletWebRequest request) throws ValidationCodeException, ServletRequestBindingException {
        ImageCode imageCode =
                (ImageCode) sessionStrategy.getAttribute(request, ValidationCodeController.SESSION_KEY);

        // 获取请求中的验证码
        String code = ServletRequestUtils.getStringParameter(request.getRequest(), "code");

        if (StringUtils.isEmpty(code)) {
            throw new ValidationCodeException("验证码不能为空");
        }

        if (imageCode.isExpired()) {
            throw new ValidationCodeException("验证码已过期");
        }

        if (!imageCode.getCode().equalsIgnoreCase(code)) {
            throw new ValidationCodeException("验证码不匹配");
        }

        // 移除验证码
        sessionStrategy.removeAttribute(request, ValidationCodeController.SESSION_KEY);
    }

    /**
     * 生成随机背景条纹
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
