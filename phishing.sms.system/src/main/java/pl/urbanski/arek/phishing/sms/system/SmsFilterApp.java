package pl.urbanski.arek.phishing.sms.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SmsFilterApp {

  public static void main(String[] args) {
    SpringApplication.run(SmsFilterApp.class, args);
  }
}
