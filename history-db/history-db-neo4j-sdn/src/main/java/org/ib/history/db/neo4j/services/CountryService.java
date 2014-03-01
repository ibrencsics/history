package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.domain.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepo;

    public List<CountryDto> getCountries() {
        List<CountryDto> countryDtos = new ArrayList<CountryDto>();

        EndResult<Country> countries = countryRepo.findAll();

        Iterator<Country> it = countries.iterator();

        while (it.hasNext()) {
            Country country = it.next();
            countryDtos.add(new CountryDto().withId(country.getId()).withName(country.getName()));
        }

        return countryDtos;
    }

    public void addCountry(String name) {
        countryRepo.save(new Country(name));
    }

    public void deleteCountry(CountryDto countryDto) {
        Country country = new Country(countryDto.getName());
        country.setId(countryDto.getId());
        countryRepo.delete(country);
    }
}
