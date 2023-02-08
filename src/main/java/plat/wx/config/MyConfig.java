package plat.wx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiefc
 */
@Data
@Configuration
@ConfigurationProperties("my.wx")
public class MyConfig {

    /**
     * openAiToken
     */
    public String openAiToken;

    /**
     * openAiModel
     */
    public String openAiModel;

    /**
     * openAiUser
     */
    public String openAiUser;

    /**
     * 微信token
     */
    public String wxToken;

}