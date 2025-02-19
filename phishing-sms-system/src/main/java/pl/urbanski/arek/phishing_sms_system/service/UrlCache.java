package pl.urbanski.arek.phishing_sms_system.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UrlCache {

  private final Map<String, Boolean> cache = new HashMap<>();

  public Boolean get(String url) {
    return cache.get(url);
  }

  public void put(String url, boolean isSafe) {
    cache.put(url, isSafe);
  }
}
