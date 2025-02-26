package pl.urbanski.arek.phishing.sms.system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.urbanski.arek.phishing.sms.system.model.Subscription;
import pl.urbanski.arek.phishing.sms.system.repository.SubscriptionRepository;

class SubscriptionServiceTest {

  private SubscriptionService subscriptionService;
  private SubscriptionRepository subscriptionRepository;

  @BeforeEach
  public void setUp() {
    subscriptionRepository = mock(SubscriptionRepository.class);

    when(subscriptionRepository.existsByPhoneNumber(anyString())).thenReturn(false);

    when(subscriptionRepository.save(any(Subscription.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    subscriptionService = new SubscriptionService(subscriptionRepository);
  }

  @Test
  void shouldSubscribe() {

    String phoneNumber = "600123456";
    when(subscriptionRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);
    assertTrue(subscriptionService.isSubscribed(phoneNumber));
  }

  @Test
  void shouldUnsubscribe() {

    String phoneNumber = "600123456";
    when(subscriptionRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);
    assertTrue(subscriptionService.isSubscribed(phoneNumber));

    subscriptionService.unsubscribe(phoneNumber);
    when(subscriptionRepository.existsByPhoneNumber(phoneNumber)).thenReturn(false);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));
  }

  @Test
  void shouldNotSubscribeDuplicated() {

    String phoneNumber = "600123456";
    subscriptionService.subscribe(phoneNumber);
    subscriptionService.subscribe(phoneNumber);
    when(subscriptionRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);
    assertTrue(subscriptionService.isSubscribed(phoneNumber));

    subscriptionService.unsubscribe(phoneNumber);
    when(subscriptionRepository.existsByPhoneNumber(phoneNumber)).thenReturn(false);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));
  }

  @Test
  void shouldNotUnsubscribeNotPresent() {

    String phoneNumber = "600123456";
    when(subscriptionRepository.existsByPhoneNumber(phoneNumber)).thenReturn(false);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));

    subscriptionService.unsubscribe(phoneNumber);
    assertFalse(subscriptionService.isSubscribed(phoneNumber));
  }
}