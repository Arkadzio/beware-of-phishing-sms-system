package pl.urbanski.arek.phishing_sms_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import pl.urbanski.arek.phishing_sms_system.model.Sms;

class SmsServiceTest {

  @Test
  void shouldProcessSmsStart() {
    SubscriptionService subscriptionService = mock(SubscriptionService.class);
    UrlChecker urlChecker = mock(UrlChecker.class);
    SmsService smsService = new SmsService(subscriptionService, urlChecker);

    Sms sms = new Sms("600123456", "700654321", "START");

    String result = smsService.processSms(sms);
    assertEquals("Subscription activated", result);

    verify(subscriptionService).subscribe("600123456");
  }

  @Test
  void shouldProcessSmsStop() {
    SubscriptionService subscriptionService = mock(SubscriptionService.class);
    UrlChecker urlChecker = mock(UrlChecker.class);
    SmsService smsService = new SmsService(subscriptionService, urlChecker);

    Sms sms = new Sms("600123456", "700654321", "STOP");

    String result = smsService.processSms(sms);
    assertEquals("Subscription deactivated", result);

    verify(subscriptionService).unsubscribe("600123456");
  }

  @Test
  void shouldProcessSmsPhishingDetect() {
    SubscriptionService subscriptionService = mock(SubscriptionService.class);
    UrlChecker urlChecker = mock(UrlChecker.class);
    SmsService smsService = new SmsService(subscriptionService, urlChecker);

    when(subscriptionService.isSubscribed("700654321")).thenReturn(true);

    String message = "New promotion for you: https://www.not-a-phishing-site.com";
    Sms sms = new Sms("600123456", "700654321", message);

    when(urlChecker.isPhishing("https://www.not-a-phishing-site.com")).thenReturn(true);

    String result = smsService.processSms(sms);
    assertEquals("!Phishing detected! Message has been blocked", result);
  }

  @Test
  void shouldProcessSmsDeliveryForSafeUrl() {
    SubscriptionService subscriptionService = mock(SubscriptionService.class);
    UrlChecker urlChecker = mock(UrlChecker.class);
    SmsService smsService = new SmsService(subscriptionService, urlChecker);

    when(subscriptionService.isSubscribed("700654321")).thenReturn(true);

    String message = "New promotion for you: https://www.a-fishing-site.com";
    Sms sms = new Sms("600123456", "700654321", message);

    when(urlChecker.isPhishing("https://www.a-fishing-site.com")).thenReturn(false);

    String result = smsService.processSms(sms);
    assertEquals("Message delivered", result);
  }

  @Test
  void shouldProcessSmsDeliveryForNonSubscriber() {
    SubscriptionService subscriptionService = mock(SubscriptionService.class);
    UrlChecker urlChecker = mock(UrlChecker.class);
    SmsService smsService = new SmsService(subscriptionService, urlChecker);

    when(subscriptionService.isSubscribed("700654321")).thenReturn(false);

    String message = "New promotion for you: https://www.not-a-phishing-site.com";
    Sms sms = new Sms("600123456", "700654321", message);

    String result = smsService.processSms(sms);
    assertEquals("Message delivered", result);
  }
}