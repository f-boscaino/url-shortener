package it.pagopa.shortener.shortener.application;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import org.apache.commons.validator.ValidatorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UrlShortenerServiceIT {

    @Autowired
    UrlShortenerService underTest;

    @Test
    public void itShouldReturnTheLongUrlIfValidationsAreOk() throws ValidatorException {
        // Given
        String longUrl = "http://www.example.com";


        // When
        UrlMapping savedUrl = underTest.save(new UrlMapping(longUrl));

        // Then
        Optional<UrlMapping> response = underTest.getByShortUrl(savedUrl.getShortUrl());

        assertTrue(response.isPresent());
        assertEquals(longUrl, response.get().getLongUrl());
    }


    @Test
    public void itShouldReturnXXXIfTheUrlIsExpired() throws ValidatorException, InterruptedException {
        // Given
        String longUrl = "http://www.example.com";
        UrlMapping urlMapping = new UrlMapping(longUrl);
        urlMapping.setExpiration(1L);

        // When
        UrlMapping savedUrl = underTest.save(urlMapping);
        waitOneSecond();

        // Then
        Optional<UrlMapping> response = underTest.getByShortUrl(savedUrl.getShortUrl());
        assertTrue(response.isEmpty());
    }

    @Test
    public void itShouldThrowAnExceptionIfTheUrlIsMalformed() {
        // Given
        String longUrl = "invalid-url";

        // Then
        assertThrows(ValidatorException.class, () -> underTest.save(new UrlMapping(longUrl)));
    }

    private static void waitOneSecond() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }
}
