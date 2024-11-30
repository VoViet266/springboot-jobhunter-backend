package vn.hoidanit.jobhunter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //cau hinh RestTemplate de goi cac API khac ben ngoai
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
