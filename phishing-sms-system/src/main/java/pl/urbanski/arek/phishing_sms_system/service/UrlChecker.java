package pl.urbanski.arek.phishing_sms_system.service;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UrlChecker {

  private static final String API_URL = "https://web-risk-api-host.com/v1:evaluateUri";
  private static final String API_KEY = "test-1234567890";

  private final RestTemplate restTemplate;
  private final UrlCache urlCache;

  @Autowired
  public UrlChecker(RestTemplate restTemplate, UrlCache urlCache) {
    this.restTemplate = restTemplate;
    this.urlCache = urlCache;
  }

  public boolean isPhishing(String urlToCheck) {
    Boolean cachedResult = urlCache.get(urlToCheck);
    if (cachedResult != null) {
      return !cachedResult;
    }

    try {
      JSONObject jsonRequest = new JSONObject();
      jsonRequest.put("uri", urlToCheck);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(API_KEY);

      HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest.toString(), headers);

      ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

      if (response.getStatusCode() == HttpStatus.OK) {
        JSONObject jsonResponse = new JSONObject(response.getBody());
        boolean isSafe = jsonResponse.getBoolean("isSafe");
        urlCache.put(urlToCheck, isSafe);
        return !isSafe;
      }
    } catch (JSONException e) {
      throw new RuntimeException("Error during JSON creation", e);
    } catch (Exception e) {
      e.printStackTrace();

      boolean isPhishingFallback = urlToCheck.toLowerCase().contains("phishing");
      urlCache.put(urlToCheck, !isPhishingFallback);
      return isPhishingFallback;

//      urlCache.put(urlToCheck, false);
//      return true;
    }
    urlCache.put(urlToCheck, true);
    return false;
  }
}
