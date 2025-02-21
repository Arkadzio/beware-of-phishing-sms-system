package pl.urbanski.arek.phishing_sms_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class UrlCheckerTest {

  private RestTemplate restTemplate;
  private UrlCache urlCache;
  private UrlChecker urlChecker;

  private static final String API_URL = "https://web-risk-api-host.com/v1:evaluateUri";
  private static final String API_KEY = "test-1234567890";

  @BeforeEach
  public void setUp() {
    restTemplate = mock(RestTemplate.class);
    urlCache = mock(UrlCache.class);
    urlChecker = new UrlChecker(restTemplate, urlCache);
  }

  @Test
  void testIsPhishingFromCache() {
    String url = "https://plaaay.pl";

    when(urlCache.get(url)).thenReturn(true);
    assertFalse(urlChecker.isPhishing(url));

    when(urlCache.get(url)).thenReturn(false);
    assertTrue(urlChecker.isPhishing(url));

    verify(urlCache, times(2)).get(url);
  }

  @Test
  void testIsPhishingSafeUrlFromApi() throws JSONException {
    String url = "https://plaaay.pl";
    when(urlCache.get(url)).thenReturn(null);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("isSafe", true);
    ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    when(restTemplate.exchange(eq(API_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
        .thenReturn(responseEntity);

    boolean result = urlChecker.isPhishing(url);
    assertFalse(result);
    verify(urlCache).put(url, true);
  }

  @Test
  void testIsPhishingPhishingUrlFromApi() throws JSONException {
    String url = "https://plaaay.pl";
    when(urlCache.get(url)).thenReturn(null);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("isSafe", false);
    ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    when(restTemplate.exchange(eq(API_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
        .thenReturn(responseEntity);

    boolean result = urlChecker.isPhishing(url);
    assertTrue(result);
    verify(urlCache).put(url, false);
  }
}