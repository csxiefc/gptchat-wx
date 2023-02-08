package plat.wx.model;

import lombok.Data;

@Data
public class TextMessage extends BaseMessage{
    // @ApiModelProperty(value = "消息类型", hidden = true)
    private String MsgType = "text";

    // 消息内容
    private String Content;
}

