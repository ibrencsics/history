package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CountryService {

    @Autowired
    CountryRepository countryRepo;

    public List<CountryDto> getCountries() {
        List<CountryDto> countryDtos = new ArrayList<CountryDto>();

        Result<Country> countries = countryRepo.findAll();

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

    public CountryDto addCountry(CountryDto countryDto) {
        Country country = countryRepo.save(new Country(countryDto));
        return new CountryDto().withId(country.getId()).withName(country.getName());
    }

    public CountryData addCountry(CountryData countryData) {
        Country country = new Country(countryData);
        for (Country.Translation translation : country.getLocales()) {
            countryRepo.save(translation.getTranslation());
        }

        Country countryCreated = countryRepo.save(country);
        return null;
    }

    public void deleteCountry(CountryDto countryDto) {
        Country country = new Country(countryDto.getName());
        country.setId(countryDto.getId());
        countryRepo.delete(country);
    }
}
