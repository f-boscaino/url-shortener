package it.pagopa.shortener.shortener.domain;

import java.time.LocalDate;
import java.util.UUID;


import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

//@Data
@RedisHash("UrlMapping")
//@NoArgsConstructor
public class UrlMapping {
	
	@Id
	private String shortUrl;
	private String longUrl;
	private LocalDate expiration;
	
	private void validateUrl(String url) throws ValidatorException {
		if (!new UrlValidator().isValid(url))  {
			throw new ValidatorException("Malformed URL");
		}
	}

	
	public UrlMapping(String longUrl) throws ValidatorException {
		validateUrl(longUrl);
		this.shortUrl = generateShortUrl();
		this.longUrl = longUrl;
		this.expiration = LocalDate.now().plusDays(30);
	}

	public UrlMapping clone() {
		return new UrlMapping(this.shortUrl, this.longUrl, this.expiration);
	}

	private UrlMapping(String shortUrl, String longUrl, LocalDate expiration) {
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
		this.expiration = expiration;
	}

	private String generateShortUrl() {
		return UUID.randomUUID().toString().substring(0, 8);
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

	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}

	public UrlMapping() {
	}
}
