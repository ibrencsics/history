package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.domain.DataTransformer;
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
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepo;

    @Override
    public List<CountryData> getCountries() {
        return getCountries(countryRepo.getCountries());
    }

    @Override
    public List<CountryData> getCountries(int start, int length) {
        return getCountries(countryRepo.getCountries(start, length));
    }

    private List<CountryData> getCountries(List<Country> countryList) {
        List<CountryData> countryDataList = new ArrayList<>();

        for (Country country : countryList) {
            countryDataList.add(DataTransformer.transform(country));
        }

        return countryDataList;
    }

    @Override
    public List<CountryData> getCountriesByPattern(String pattern) {
        List<CountryData> countryDataList = new ArrayList<>();

        List<Country> countryList = countryRepo.getCountriesByPattern("(?i).*" + pattern + ".*");
        for (Country country : countryList) {
            countryDataList.add(DataTransformer.transform(country));
        }

        return countryDataList;
    }

    @Override
    public List<CountryData> getCountriesById(List<CountryData> countryOnlyIds) {
        List<CountryData> countriesFull = new ArrayList<>();

        for (CountryData countryIdOnly : countryOnlyIds) {
            countriesFull.add( DataTransformer.transform( countryRepo.findOne(countryIdOnly.getId()) ) );
        }

        return countriesFull;
    }

    @Override
    public CountryData addCountry(CountryData countryData) {
        Country country = DataTransformer.transform(countryData);
        for (Country.Translation<Country> translation : country.getLocales()) {
            countryRepo.save(translation.getTranslation());
        }

        Country countryCreated = countryRepo.save(country);
        return DataTransformer.transform(countryCreated);
    }

    @Override
    public void deleteCountry(CountryData countryData) {
        countryRepo.delete(countryData.getId());
    }
}
