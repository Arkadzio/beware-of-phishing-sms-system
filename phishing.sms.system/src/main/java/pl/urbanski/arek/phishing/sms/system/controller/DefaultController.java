package pl.urbanski.arek.phishing.sms.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

  @GetMapping("/")
  public String home() {
    return "Welcome to the Phishing SMS System";
  }
}
