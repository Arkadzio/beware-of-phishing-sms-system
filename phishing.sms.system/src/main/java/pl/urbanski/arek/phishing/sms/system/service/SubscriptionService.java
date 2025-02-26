package pl.urbanski.arek.phishing.sms.system.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.urbanski.arek.phishing.sms.system.model.Subscription;
import pl.urbanski.arek.phishing.sms.system.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;

  @Autowired
  public SubscriptionService(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  public void subscribe(String phoneNumber) {
    if (!subscriptionRepository.existsByPhoneNumber(phoneNumber)) {
      Subscription subscription = new Subscription(phoneNumber);
      subscriptionRepository.save(subscription);
    }
  }

  public void unsubscribe(String phoneNumber) {
    subscriptionRepository.deleteByPhoneNumber(phoneNumber);
  }

  public boolean isSubscribed(String phoneNumber) {
    return subscriptionRepository.existsByPhoneNumber(phoneNumber);
  }
}
