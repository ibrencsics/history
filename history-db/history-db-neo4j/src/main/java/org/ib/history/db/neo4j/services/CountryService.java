package org.ib.history.db.neo4j.services;

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

    public List<String> getCountries() {
        List<String> countryNames = new ArrayList<String>();

        EndResult<Country> countries = countryRepo.findAll();

        Iterator<Country> it = countries.iterator();

        while (it.hasNext()) {
            countryNames.add(it.next().getName());
        }

        return countryNames;
    }
}
