package org.acme.dto;

public class CountriesGetResponse {
    public String country;
    public String demonym;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getDemonym() {
        return this.demonym;
    }

    public void setDemonym(final String demonym) {
        this.demonym = demonym;
    }
}
