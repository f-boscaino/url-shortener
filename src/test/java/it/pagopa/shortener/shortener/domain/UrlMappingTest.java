package it.pagopa.shortener.shortener.domain;

import org.apache.commons.validator.ValidatorException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UrlMappingTest {

    @Test
    public void anUrlMappingIsCreatedSuccessfully() throws ValidatorException {
        // When
        UrlMapping underTest = new UrlMapping("http://www.example.com");

        // Then
        assertNotNull(underTest.getShortUrl());
    }

    @Test
    public void anUrlMappingWithAMalformedUrlShouldThrowAnException() {
        assertThrows(ValidatorException.class, () -> new UrlMapping("bad-url"));
    }
}