package pl.urbanski.arek.phishing.sms.system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UrlCacheEntry {

  @Id
  private String url;
  private boolean isSafe;

  public UrlCacheEntry() {
  }

  public UrlCacheEntry(String url, boolean isSafe) {
    this.url = url;
    this.isSafe = isSafe;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isSafe() {
    return isSafe;
  }

  public void setSafe(boolean safe) {
    isSafe = safe;
  }
}
