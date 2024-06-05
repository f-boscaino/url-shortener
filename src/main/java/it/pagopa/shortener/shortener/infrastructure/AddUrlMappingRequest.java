package it.pagopa.shortener.shortener.infrastructure;

import it.pagopa.shortener.shortener.domain.UrlMapping;
import org.apache.commons.validator.ValidatorException;

public class AddUrlMappingRequest {
    private String url;

    public UrlMapping toUrlMapping() throws ValidatorException {
        return new UrlMapping(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
