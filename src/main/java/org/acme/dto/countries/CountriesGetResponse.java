package org.acme.dto.countries;

import java.util.List;

public class CountriesGetResponse {
    private CountryName name;
    private Demonyms demonyms;

    public CountryName getName() {
        return name;
    }

    public void setName(final CountryName name){
        this.name = name;
    }

    public Demonyms getDemonyms(){
        return this.demonyms;
    }

    public void setDemonyms(Demonyms demonyms){
        this.demonyms = demonyms;
    }
}
