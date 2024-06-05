package it.pagopa.shortener.shortener.application;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import it.pagopa.shortener.shortener.domain.UrlMappingRepository;
import it.pagopa.shortener.shortener.domain.UrlShortenerServicePort;
import org.apache.commons.validator.ValidatorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService implements UrlShortenerServicePort {

    private UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public Optional<UrlMapping> getByShortUrl(String shortUrl) {
        return urlMappingRepository.findById(shortUrl);
    }

    public UrlMapping save(UrlMapping urlMapping) throws ValidatorException {
        urlMapping = getUniqueUrlMapping(urlMapping);
        return urlMappingRepository.save(urlMapping);
    }

    /**
     * @param urlMapping
     * @return an UrlMapping object with a unique ID
     * @throws ValidatorException
     */
    private UrlMapping getUniqueUrlMapping(UrlMapping urlMapping) throws ValidatorException {
        UrlMapping currentUrlMapping = urlMapping.clone();

        while (getByShortUrl(currentUrlMapping.getShortUrl()).isPresent()) {
            currentUrlMapping = new UrlMapping(currentUrlMapping.getLongUrl());
        }

        return currentUrlMapping;
    }


}
