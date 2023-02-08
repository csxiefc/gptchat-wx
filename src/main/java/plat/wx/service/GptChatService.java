package plat.wx.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plat.wx.config.MyConfig;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class GptChatService {

    @Resource
    MyConfig myConfig;

    public String chat(String prompt,String user) {
        return this.chatCore(myConfig.getOpenAiModel(),prompt,user);
    }

    public String chatCore(String model,String prompt,String user) {
        StringBuilder result = new StringBuilder(100);
        OpenAiService service = new OpenAiService(myConfig.getOpenAiToken(),40000);
        System.out.println("\nCreating completion...");
        /*
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt("Somebody once told me the world is gonna roll me")
                .echo(true)
                .user("testing")
                .build();

         */
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model(model)
                .prompt(prompt)
                .temperature(0.9)
                .maxTokens(500)
                //.topP(1.0)
                //.n(1)
                //.stream(true)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                //.echo(true)
                .user(user)
                .build();
        // service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        for(CompletionChoice choice : choices) {
            /*
            if(result.toString().length() != 0) {
                result.append(System.lineSeparator()); // 系统换行符
            }
             */
            result.append(choice.getText());
            log.info("choice="+choice);
        }
        return result.toString();
    }
}
