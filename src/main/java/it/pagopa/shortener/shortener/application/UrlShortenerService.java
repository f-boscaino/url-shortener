package it.pagopa.shortener.shortener.application;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import it.pagopa.shortener.shortener.domain.UrlMappingRepository;
import it.pagopa.shortener.shortener.domain.UrlShortenerServicePort;
import org.apache.commons.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService implements UrlShortenerServicePort {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerService.class);

    private final UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    /**
     * Returns an UrlMapping object retrieved from Redis, using the shortUrl as a key
     *
     * @param shortUrl the shortUrl to find
     * @return the matching UrlMapping, if found
     */
    public Optional<UrlMapping> getByShortUrl(String shortUrl) {
        return urlMappingRepository.findById(shortUrl);
    }

    /**
     * Persists the input UrlMapping, generating a new shortUrl as a key. If the shortUrl has already been assigned
     * to a different UrlMapping, it generates a new one.
     *
     * @param urlMapping the UrlMapping to persist
     * @return the persisted UrlMapping, containing a generated unique shortUrl
     * @throws ValidatorException if the input longUrl is not a valid URL
     */
    public UrlMapping save(UrlMapping urlMapping) throws ValidatorException {
        urlMapping = getUniqueUrlMapping(urlMapping);
        logger.info(urlMapping.getLongUrl() + " mapped to: " + urlMapping.getShortUrl());
        return urlMappingRepository.save(urlMapping);
    }

    /**
     * Returns an UrlMapping with a unique shortUrl key. If the input object has a shortUrl already assigned to a
     * different UrlMapping on Redis, it updates the UrlMapping with a new shortUrl.
     *
     * @param urlMapping the UrlMapping to check
     * @return an UrlMapping object with a unique ID
     * @throws ValidatorException if the input longUrl is not a valid URL
     */
    private UrlMapping getUniqueUrlMapping(UrlMapping urlMapping) throws ValidatorException {
        UrlMapping currentUrlMapping = urlMapping.clone();

        while (getByShortUrl(currentUrlMapping.getShortUrl()).isPresent()) {
            logger.debug(urlMapping.getShortUrl() + " already exists! Generating a new one");
            currentUrlMapping = new UrlMapping(currentUrlMapping.getLongUrl());
        }

        return currentUrlMapping;
    }


}
