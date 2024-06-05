package it.pagopa.shortener.shortener.domain;

import org.apache.commons.validator.ValidatorException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UrlMappingTest {

    @Test
    public void anUrlMappingIsCreatedSuccessfully() throws ValidatorException {
        UrlMapping underTest = new UrlMapping("http://www.example.com");
        assertNotNull(underTest.getShortUrl());
        assertEquals(LocalDate.now().plusDays(30), underTest.getExpiration());
    }

    @Test
    public void anUrlMappingWithAMalformedUrlShouldThrowAnException() {
        assertThrows(ValidatorException.class, () -> new UrlMapping("bad-url"));
    }
}