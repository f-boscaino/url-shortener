package it.pagopa.shortener.shortener.application;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import it.pagopa.shortener.shortener.domain.UrlMappingRepository;
import org.apache.commons.validator.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    UrlMappingRepository urlMappingRepositoryMock;

    @Test
    void itShouldRetryCreatingANewShortUrlIfItAlreadyExists() throws ValidatorException {
        UrlShortenerService underTest = new UrlShortenerService(urlMappingRepositoryMock);

        // Input object, it has a non-unique key
        UrlMapping urlMappingWithExistingId = new UrlMapping("http://www.example.com");
        urlMappingWithExistingId.setShortUrl("existing-short-url");

        UrlMapping existingUrlMapping = new UrlMapping("http://www.example.com");
        existingUrlMapping.setShortUrl("new-short-url");

        // Any calls to the getReferenceById() method returns null, except for the "existing-short-url" key
        when(urlMappingRepositoryMock.findById(any())).thenReturn(Optional.empty());
        // First time, in the "while" loop, it returns a new object with a unique key
        when(urlMappingRepositoryMock.findById(eq("existing-short-url"))).thenReturn(Optional.of(existingUrlMapping));
        // The save() method returns the first parameter (the persisted object)
        when(urlMappingRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);


        UrlMapping returnValue = underTest.save(urlMappingWithExistingId);

        verify(urlMappingRepositoryMock, times(1)).findById(eq("existing-short-url"));
        verify(urlMappingRepositoryMock, times(1)).findById(eq(returnValue.getShortUrl()));
    }
}