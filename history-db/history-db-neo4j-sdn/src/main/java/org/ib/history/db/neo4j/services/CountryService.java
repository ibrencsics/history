package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.CountryDto;
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
public class CountryService {

    @Autowired
    CountryRepository countryRepo;

    public List<CountryData> getCountries() {
        List<CountryData> countryDataList = new ArrayList<>();

        List<Country> countryList = countryRepo.getAllCountries();
        for (Country country : countryList) {
            countryDataList.add(DataTransformer.transform(country));
        }

        return countryDataList;
    }

    public CountryData addCountry(CountryData countryData) {
        Country country = DataTransformer.transform(countryData);
        for (Country.Translation translation : country.getLocales()) {
            countryRepo.save(translation.getTranslation());
        }

        Country countryCreated = countryRepo.save(country);
        return DataTransformer.transform(countryCreated);
    }

    public void deleteCountry(CountryData countryData) {
        countryRepo.delete(countryData.getId());
    }
}
