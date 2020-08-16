package com.cjz.image.validation.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码类
 * @author Kamchou
 */
@Data
public class ImageCode {

    /**
     * 流
     */
    private BufferedImage bufferedImage;

    /**
     * 验证码
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 检查是否过期
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    /**
     * 构造一个过期时间
     * @param bufferedImage 流
     * @param code          验证码
     * @param expireIn      多少秒内过期
     */
    public ImageCode(BufferedImage bufferedImage, String code, long expireIn) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }
}
