package pl.urbanski.arek.phishing.sms.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.urbanski.arek.phishing.sms.system.model.UrlCacheEntry;

public interface UrlCacheRepository extends JpaRepository<UrlCacheEntry, String> {
}
