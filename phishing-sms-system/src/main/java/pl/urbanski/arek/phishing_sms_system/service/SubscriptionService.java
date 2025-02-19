package pl.urbanski.arek.phishing_sms_system.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

  private final Set<String> subscribedNumbers = new HashSet<>();

  public void subscribe(String phoneNumber) {
    subscribedNumbers.add(phoneNumber);
  }

  public void unsubscribe(String phoneNumber) {
    subscribedNumbers.remove(phoneNumber);
  }

  public boolean isSubscribed(String phoneNumber) {
    return subscribedNumbers.contains(phoneNumber);
  }
}
