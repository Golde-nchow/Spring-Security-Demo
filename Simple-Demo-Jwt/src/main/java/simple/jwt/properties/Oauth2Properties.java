package simple.jwt.properties;

import lombok.Data;

/**
 * @author User
 * @description oauth2配置项
 * @date 2020/9/23 23:09
 */
@Data
public class Oauth2Properties {

    /**
     * 用于签名的密钥
     */
    private String signingKey = "cjz";

}
