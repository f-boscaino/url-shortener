package it.pagopa.shortener.shortener.domain;

import org.apache.commons.validator.ValidatorException;

import java.util.Optional;

public interface UrlShortenerServicePort {

    Optional<UrlMapping> getByShortUrl(String shortUrl);
    UrlMapping save(UrlMapping urlMapping) throws ValidatorException;

}
