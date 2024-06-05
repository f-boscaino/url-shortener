package it.pagopa.shortener.shortener.domain;

import java.time.LocalDate;
import java.util.UUID;


import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("UrlMapping")
public class UrlMapping {

    private static final Long EXPIRATION_IN_SECONDS = 2592000L;
    @Id
    private String shortUrl;
    private String longUrl;
    @TimeToLive
    private Long expiration = EXPIRATION_IN_SECONDS;

    public UrlMapping() {
    }

    private UrlMapping(String shortUrl, String longUrl, Long expiration) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
		this.expiration = expiration;
    }

    public UrlMapping(String longUrl) throws ValidatorException {
        validateUrl(longUrl);
        this.shortUrl = generateShortUrl();
        this.longUrl = longUrl;
    }

    public UrlMapping clone() {
        return new UrlMapping(this.shortUrl, this.longUrl, this.expiration);
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    private void validateUrl(String url) throws ValidatorException {
        if (!new UrlValidator().isValid(url)) {
            throw new ValidatorException("Malformed URL");
        }
    }

    private String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
