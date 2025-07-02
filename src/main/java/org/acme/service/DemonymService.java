package org.acme.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.dto.countries.CountriesGetResponse;

import java.util.List;

@Path("/v3.1/alpha/{country}")
public interface DemonymService {

    @GET
    List<CountriesGetResponse> getDemonynByCountryName(@PathParam("country") String country);
}
