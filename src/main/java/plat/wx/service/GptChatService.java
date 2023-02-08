package plat.wx.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plat.wx.config.MyConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * GptChat服务类
 *
 * @author xiefc
 */
@Slf4j
@Service
public class GptChatService {

    @Resource
    MyConfig myConfig;

    public String chat(String prompt,String user) {
        return this.chatCore(myConfig.getOpenAiModel(),prompt,user);
    }

    /**
     * @param model
     * @param prompt
     * @param user
     * @return
     */
    public String chatCore(String model,String prompt,String user) {
        StringBuilder result = new StringBuilder(100);
        try {
            OpenAiService service = new OpenAiService(myConfig.getOpenAiToken());
            log.info("\nCreating completion...");
            CompletionRequest completionRequest = CompletionRequest.builder()
                    .model(model)
                    .prompt(prompt)
                    .temperature(0.9)
                    .maxTokens(100)
                    .topP(1.0)
                    .n(1)
                    //.stream(true)
                    .frequencyPenalty(0.0)
                    .presencePenalty(0.6)
                    .echo(true)
                    .user(user)
                    .build();
            List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
            for(CompletionChoice choice : choices) {
                result.append(choice.getText());
                log.info("choice="+choice);
            }
        } catch (Exception e) {
            log.error("chatCore异常",e);
            result.append(ExceptionUtil.getRootCause(e));
        }
        return result.toString();
    }
}
