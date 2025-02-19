package pl.urbanski.arek.phishing_sms_system.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import pl.urbanski.arek.phishing_sms_system.model.Sms;

@Service
public class SmsService {

  private final SubscriptionService subscriptionService;
  private final UrlChecker urlChecker;

  public SmsService(SubscriptionService subscriptionService, UrlChecker urlChecker) {
    this.subscriptionService = subscriptionService;
    this.urlChecker = urlChecker;
  }

  public String processSms(Sms sms) {
    String message = sms.getMessage().trim();

    if (message.equalsIgnoreCase("START")) {
      subscriptionService.subscribe(sms.getSender());
      return "Subscription activated";
    } else if (message.equalsIgnoreCase("STOP")) {
      subscriptionService.unsubscribe(sms.getSender());
      return "Subscription deactivated";
    }
    if (subscriptionService.isSubscribed(sms.getRecipient())) {
      Matcher matcher = Pattern.compile("(http?://\\S+)").matcher(message);
      while (matcher.find()) {
        String url = matcher.group(1);
        if (urlChecker.isPhishing(url)) {
          return "!Phishing detected! Message has been blocked";
        }
      }
    }
    return "Message delivered";
  }
}
