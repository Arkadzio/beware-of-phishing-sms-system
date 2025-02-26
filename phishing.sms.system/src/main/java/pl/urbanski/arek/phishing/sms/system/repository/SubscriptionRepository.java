package pl.urbanski.arek.phishing.sms.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.urbanski.arek.phishing.sms.system.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

  boolean existsByPhoneNumber(String phoneNumber);

  void deleteByPhoneNumber(String phoneNumber);
}
