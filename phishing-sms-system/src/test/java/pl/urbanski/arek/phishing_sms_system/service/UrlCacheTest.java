package pl.urbanski.arek.phishing_sms_system.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlCacheTest {

  private UrlCache urlCache;

  @BeforeEach
  public void setUp() {
    urlCache = new UrlCache();
  }

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