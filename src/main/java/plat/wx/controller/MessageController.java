
package plat.wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import plat.wx.service.CheckSignatureService;
import plat.wx.service.GptChatService;
import plat.wx.service.MessageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 微信第三方服务器接口
 */
@Slf4j
@RestController
@RequestMapping("/wxapi")
public class MessageController {

    @Resource
    MessageService messageService;

    @Resource
    GptChatService gptChatService;

    @Resource
    CheckSignatureService checkSignatureService;

    /**
     * 微信公众号自动回复给发消息者
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public void callback(HttpServletRequest request, HttpServletResponse response){
        try {
            log.info("callback post begin...");
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            // 构建自动回复消息
            String result = messageService.messageRequest(request);
            // 回复消息内容
            log.info("wxConnect result="+result);
            PrintWriter out = response.getWriter();
            out.write(result);
            log.info("callback post end");
        } catch (Exception e) {
            log.error("接收微信消息异常",e);
        }
    }


    /**
     * 微信验证第三方服务接口
     *
     * @param req
     * @param resp
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void callback_check(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 获取微信服务器传来的相关参数
            String signature = req.getParameter("signature");
            String timestamp = req.getParameter("timestamp");
            String nonce = req.getParameter("nonce");
            String echoStr = req.getParameter("echostr");
            // 展示微信服务器传来的相关参数
            log.info("signature="+signature+",timestamp="+timestamp+",nonce="+nonce+",echostr="+echoStr);
            PrintWriter out = resp.getWriter();
            //调用比对signature的方法，实现对token和传入的参数进行hash算法后的结果比对
            if(checkSignatureService.checkSignature(signature, timestamp, nonce)){
                // 验证通过
                log.info("callback check success!!!");
                out.print(echoStr);
            }
        } catch (Exception e) {
            log.error("验证微信服务器是否有效异常",e);
        }
    }

    /**
     * 回调接口测试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/callbackTest", method = RequestMethod.GET)
    public void callbackTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            // 接收查询参数
            String q = request.getParameter("q");
            // 返回前端值
            String result = q + " callbackTest backgroud:" + System.currentTimeMillis();
            PrintWriter out = response.getWriter();
            out.print(result);
        } catch (Exception e) {
            log.error("测试接口异常",e);
        }
    }

    /**
     * callbackGptTest 回调接口测试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/callbackGptTest", method = RequestMethod.GET)
    public void callbackGptTest(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            // 接收查询参数
            String model = request.getParameter("model");
            String prompt = request.getParameter("prompt");
            String user = request.getParameter("user");
            // 返回前端值
            String result = this.gptChatService.chatCore(model,prompt,user);
            PrintWriter out = response.getWriter();
            out.print(result);
        } catch (Exception e) {
            log.error("callbackGptTest测试接口异常",e);
            try {
                PrintWriter out = response.getWriter();
                out.print(e);
            } catch (Exception e2) {}

        }
    }

}
