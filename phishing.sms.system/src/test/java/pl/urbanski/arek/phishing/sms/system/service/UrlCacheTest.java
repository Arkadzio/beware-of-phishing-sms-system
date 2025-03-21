package pl.urbanski.arek.phishing.sms.system.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.urbanski.arek.phishing.sms.system.repository.UrlCacheRepository;

@SpringBootTest
class UrlCacheTest {

  @Autowired
  private UrlCache urlCache;

  @Test
  void shouldGetNullWhenUrlNotExists() {
    String url = "http://thereisnothing.pl";
    assertNull(urlCache.get(url));
  }

  @Test
  void shouldPutAndGet() {
    String url = "http://plaaay.com";

    urlCache.put(url, true);
    assertTrue(urlCache.get(url));

    urlCache.put(url, false);
    assertFalse(urlCache.get(url));
  }
}