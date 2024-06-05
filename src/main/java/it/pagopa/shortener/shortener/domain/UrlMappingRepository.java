package it.pagopa.shortener.shortener.domain;

import org.springframework.data.repository.CrudRepository;


public interface UrlMappingRepository extends CrudRepository<UrlMapping, String> {
}
