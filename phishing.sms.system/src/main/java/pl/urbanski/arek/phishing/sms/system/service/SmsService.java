package pl.urbanski.arek.phishing.sms.system.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.urbanski.arek.phishing.sms.system.model.Sms;

@Service
public class SmsService {

  private static final String START_STOP_NUMBER = "48500600700";
  private final SubscriptionService subscriptionService;
  private final UrlChecker urlChecker;

  @Autowired
  public SmsService(SubscriptionService subscriptionService, UrlChecker urlChecker) {
    this.subscriptionService = subscriptionService;
    this.urlChecker = urlChecker;
  }

  public String processSms(Sms sms) {
    String message = sms.getMessage().trim();

    if (sms.getRecipient().equals(START_STOP_NUMBER)) {
      if (message.equalsIgnoreCase("START")) {
        subscriptionService.subscribe(sms.getSender());
        return "Subscription activated";
      } else if (message.equalsIgnoreCase("STOP")) {
        subscriptionService.unsubscribe(sms.getSender());
        return "Subscription deactivated";
      }
    }

    if (subscriptionService.isSubscribed(sms.getSender())) {
      Matcher matcher = Pattern.compile("((https?|ftp|file):\\/\\/\\S+)|(mailto:\\S+)|((www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\/\\S*)?)")
          .matcher(message);
      while (matcher.find()) {
        String url = matcher.group();
        if (urlChecker.isPhishing(url)) {
          return "!Phishing detected! Message has been blocked";
        }
      }
    }
    return "Message delivered";
  }
}
