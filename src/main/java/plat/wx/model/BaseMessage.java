package plat.wx.model;

import lombok.Data;


/**
 * @author xiefc
 */
@Data
public class BaseMessage {

    /**
     * 开发者微信号
     */
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;

    /**
     * 创建时间
     */
    private long CreateTime;

    /**
     * 消息类型（text/image/location/link）
     */
    private String MsgType;
}

