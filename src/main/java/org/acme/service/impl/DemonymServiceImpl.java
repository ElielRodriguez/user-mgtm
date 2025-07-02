package org.acme.service.impl;

import io.quarkus.cache.CacheKey;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.dto.CountriesGetResponse;
import org.acme.service.DemonymService;

import java.net.URI;

@ApplicationScoped
@Path("/v3.1/alpha/{country}")
public class DemonymServiceImpl {

    @Inject
    private final DemonymService demonymService;

    public DemonymServiceImpl() {
        demonymService = QuarkusRestClientBuilder
                .newBuilder()
                .baseUri(URI.create("https://restcountries.com"))
                .build(DemonymService.class);
    }

    @GET
    public CountriesGetResponse getDemonynByCountryName(@PathParam("country") @CacheKey String country) {
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country name cannot be null or blank");
        }

        var result = demonymService.getDemonynByCountryName(country.toLowerCase()).get(0);

        if (result == null || result.getName() == null || result.getDemonyms() == null) {
            throw new IllegalArgumentException("No demonym found for the provided country name: " + country);
        }

        CountriesGetResponse response = new CountriesGetResponse();
        response.setCountry(result.getName().getOfficial());
        response.setDemonym(result.getDemonyms().getEng().getM());

        return response;
    }
}
