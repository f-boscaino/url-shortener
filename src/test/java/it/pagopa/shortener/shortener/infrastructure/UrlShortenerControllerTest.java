package it.pagopa.shortener.shortener.infrastructure;

import it.pagopa.shortener.shortener.application.UrlShortenerService;
import it.pagopa.shortener.shortener.domain.UrlMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Test
    public void theGetEndpointShouldRedirectToTheLongUrl() throws Exception {
        // Given
        String shortUrl = "123";
        String longUrl = "http://www.example.com";
        Optional<UrlMapping> urlMapping = Optional.of(new UrlMapping(longUrl));
        when(urlShortenerService.getByShortUrl(shortUrl)).thenReturn(urlMapping);

        // Then
        mockMvc.perform(get("/"+shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(longUrl));
    }

    @Test
    public void theGetEndpointShouldThrow404ForMissingMapping() throws Exception {
        mockMvc.perform(get("/missingRedirectUrl"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void theAddEndpointShouldReturnAShortUrl() throws Exception {
        // Given
        String shortUrl = "123";
        String longUrl = "http://www.example.com";
        UrlMapping urlMapping = new UrlMapping(longUrl);
        urlMapping.setShortUrl(shortUrl);
        when(urlShortenerService.save(any())).thenReturn(urlMapping);

        String jsonRequest = "{ \"url\": \""+longUrl+"\"}";

        // Then
        mockMvc.perform(post("/add")
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string(shortUrl));
    }


}