package com.cjz.image.validation.controller;

import com.cjz.image.validation.code.GenerateImageCode;
import com.cjz.image.validation.code.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
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
    private GenerateImageCode generateImageCode;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 用于将信息保存在 session
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("image")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = generateImageCode.generate(67, 23, 4, 300);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getBufferedImage(), "JPEG", response.getOutputStream());
    }

}
