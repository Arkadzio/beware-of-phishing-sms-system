package pl.urbanski.arek.phishing.sms.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Value("${api.url}")
  private String apiUrl;

  @Value("${api.key}")
  private String apiKey;

  @Bean
  public String apiUrl() {
    return apiUrl;
  }

  @Bean
  public String apiKey() {
    return apiKey;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
