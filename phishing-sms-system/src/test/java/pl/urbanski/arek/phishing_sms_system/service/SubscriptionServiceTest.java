package pl.urbanski.arek.phishing_sms_system.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionServiceTest {

  private SubscriptionService subscriptionService;

  @BeforeEach
  public void setUp() {
    subscriptionService = new SubscriptionService();
  }

  @Test
  void shouldSubscribe() {

    String phoneNumber = "600123456";
    subscriptionService.subscribe(phoneNumber);
    assertTrue(subscriptionService.isSubscribed(phoneNumber));
  }

  @Test
  void shouldUnsubscribe() {

    String phoneNumber = "600123456";
    subscriptionService.subscribe(phoneNumber);
    assertTrue(subscriptionService.isSubscribed(phoneNumber));

    subscriptionService.unsubscribe(phoneNumber);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));
  }

  @Test
  void shouldNotSubscribeDuplicated() {

    String phoneNumber = "600123456";
    subscriptionService.subscribe(phoneNumber);
    subscriptionService.subscribe(phoneNumber);
    assertTrue(subscriptionService.isSubscribed(phoneNumber));

    subscriptionService.unsubscribe(phoneNumber);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));
  }

  @Test
  void shouldNotUnsubscribeNotPresent() {

    String phoneNumber = "600123456";
    assertFalse(subscriptionService.isSubscribed(phoneNumber));

    subscriptionService.unsubscribe(phoneNumber);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));
  }
}