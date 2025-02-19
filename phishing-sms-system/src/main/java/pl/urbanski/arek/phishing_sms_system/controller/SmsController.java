package pl.urbanski.arek.phishing_sms_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.urbanski.arek.phishing_sms_system.model.Sms;
import pl.urbanski.arek.phishing_sms_system.service.SmsService;

@RestController
@RequestMapping("/sms")
public class SmsController {

  private final SmsService smsService;

  public SmsController(SmsService smsService) {
    this.smsService = smsService;
  }

  @PostMapping("/receive")
  public ResponseEntity<String> receiveSms(@RequestBody Sms sms) {
    String result = smsService.processSms(sms);
    return ResponseEntity.ok(result);
  }
}
