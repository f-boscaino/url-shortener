package it.pagopa.shortener.shortener.application;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import org.apache.commons.validator.ValidatorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UrlShortenerServiceIT {

    @Autowired
    UrlShortenerService underTest;

    @Test
    public void itShouldReturnTheLongUrlIfValidationsAreOk() throws ValidatorException {
        String longUrl = "http://www.example.com";
        UrlMapping savedUrl = underTest.save(new UrlMapping(longUrl));

        Optional<UrlMapping> response = underTest.getByShortUrl(savedUrl.getShortUrl());
        assertTrue(response.isPresent());

        assertEquals(longUrl, response.get().getLongUrl());
    }

    @Test
    public void itShouldThrowAnExceptionIfTheUrlIsMalformed() {
        String longUrl = "invalid-url";
        assertThrows(ValidatorException.class, () -> underTest.save(new UrlMapping(longUrl)));
    }

}
