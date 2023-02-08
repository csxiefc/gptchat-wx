package plat.wx.service;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;
import plat.wx.model.TextMessage;
import plat.wx.util.MessageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author xiefc
 */
@Slf4j
@Service
public class MessageService  {

    @Resource
    GptChatService gptChatService;


    /**
     * 响应post请求，微信中消息和菜单交互都是采用post请求
     *
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public String messageRequest(HttpServletRequest request) throws DocumentException, IOException {
        String respMessage = "";
        // xml请求解析
        Map<String, String> requestMap = MessageUtil.xmlToMap(request);
        log.info("requestMap:"+ requestMap.toString());
        if (requestMap.size() > 0) {
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                respMessage = gptChatService.chat(content,fromUserName);
            } else if (MessageUtil.MESSAGE_IMAGE.equals(msgType)) {
                //回复图片消息
                respMessage = "hello world MESSAGE_IMAGE";
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                // 事件推送
                respMessage = "hello world MESSAGE_EVENT";
            }
            TextMessage newObj = new TextMessage();
            newObj.setFromUserName(toUserName);
            newObj.setToUserName(fromUserName);
            newObj.setContent(respMessage);
            newObj.setCreateTime(System.currentTimeMillis());

            respMessage = MessageUtil.textMessageToXml(newObj);
        }
        return respMessage;
    }



}
