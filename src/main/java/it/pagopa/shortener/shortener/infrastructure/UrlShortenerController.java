package it.pagopa.shortener.shortener.infrastructure;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import it.pagopa.shortener.shortener.domain.UrlShortenerServicePort;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.ValidatorException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController("/")
public class UrlShortenerController {

    private final UrlShortenerServicePort urlShortenerService;

    public UrlShortenerController(UrlShortenerServicePort urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Redirects the user to the URL corresponding to the input shortUrl
     *
     * @param response the HTTP request object
     * @param shortUrl the input shortUrl
     */
    @GetMapping("/{shortUrl}")
    public void redirect(HttpServletResponse response, @PathVariable String shortUrl) {
        Optional<UrlMapping> optionalUrlMapping = urlShortenerService.getByShortUrl(shortUrl);
        optionalUrlMapping.ifPresentOrElse(
                (urlMapping) -> {
                    try {
                        response.sendRedirect(urlMapping.getLongUrl());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> {
                    try {
                        response.sendError(404, "Url not found");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        );
    }

    /**
     * Generates a new unique shortUrl, persisting the association between the input URL and the generated shortUrl
     *
     * @param request the request body, containing the input URL
     * @param response HTTP response object
     * @return the generated shortUrl
     * @throws ValidatorException if the input string is not a valid URL
     */
    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestBody AddUrlMappingRequest request, HttpServletResponse response) throws ValidatorException {
        UrlMapping urlMapping = urlShortenerService.save(request.toUrlMapping());
        response.setStatus(201);
        return urlMapping.getShortUrl();
    }

}
