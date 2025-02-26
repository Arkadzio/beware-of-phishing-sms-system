package pl.urbanski.arek.phishing.sms.system.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UrlChecker {

  private final RestTemplate restTemplate;
  private final UrlCache urlCache;
  private final String apiUrl;
  private final String apiKey;

  @Autowired
  public UrlChecker(RestTemplate restTemplate,
                    UrlCache urlCache,
                    @Qualifier("apiUrl") String apiUrl,
                    @Qualifier("apiKey") String apiKey) {
    this.restTemplate = restTemplate;
    this.urlCache = urlCache;
    this.apiUrl = apiUrl;
    this.apiKey = apiKey;
  }

  public boolean isPhishing(String urlToCheck) {
    Boolean cachedResult = urlCache.get(urlToCheck);
    if (cachedResult != null) {
      log.debug("Cache hit for URL: {} - isSafe: {}", urlToCheck, cachedResult);
      return !cachedResult;
    }

    try {
      JSONObject jsonRequest = new JSONObject();
      jsonRequest.put("uri", urlToCheck);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(apiKey);

      HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest.toString(), headers);

      ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

      if (response.getStatusCode() == HttpStatus.OK) {
        log.info("Received 200 OK from API for URL: {}", urlToCheck);
        JSONObject jsonResponse = new JSONObject(response.getBody());
//        boolean isSafe = jsonResponse.getBoolean("isSafe");
        boolean isSafe = jsonResponse.optBoolean("isSafe", true);
        urlCache.put(urlToCheck, isSafe);
        return !isSafe;

      } else {

        log.warn("Unexpected response status: {} for URL: {}", response.getStatusCode(), urlToCheck);
        throw new RuntimeException("Unable to verify URL due to unexpected response status");
      }

    } catch (JSONException e) {
      log.error("Error during JSON creation for URL: {}", urlToCheck, e);
      throw new RuntimeException("Error during JSON creation", e);

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      log.error("HTTP error while checking URL: {} - Status: {} - Response: {}", urlToCheck, e.getStatusCode(), e.getResponseBodyAsString(), e);
      throw new RuntimeException("HTTP error while checking URL");

    } catch (Exception e) {
      log.error("Error while checking URL: {}", urlToCheck, e);
      throw new RuntimeException("Error while checking URL", e);
    }
  }
}
