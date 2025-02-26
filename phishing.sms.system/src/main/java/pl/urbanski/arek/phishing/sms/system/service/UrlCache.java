package pl.urbanski.arek.phishing.sms.system.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.urbanski.arek.phishing.sms.system.model.UrlCacheEntry;
import pl.urbanski.arek.phishing.sms.system.repository.UrlCacheRepository;

@Component
public class UrlCache {

  private final UrlCacheRepository repository;

  @Autowired
  public UrlCache(UrlCacheRepository repository) {
    this.repository = repository;
  }

  public Boolean get(String url) {
    return repository
        .findById(url)
        .map(UrlCacheEntry::isSafe)
        .orElse(null);
  }

  public void put(String url, boolean isSafe) {
    UrlCacheEntry entry = new UrlCacheEntry(url, isSafe);
    repository.save(entry);
  }
}
