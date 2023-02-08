package plat.wx;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@EnableAsync
@SpringBootApplication
@ServletComponentScan
public class WxAdminApplication {
    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent sce) {
                // log.info("ServletContext initialized");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {

                //log.info("ServletContext destroyed");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(WxAdminApplication.class, args);
    }
}
