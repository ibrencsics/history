package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.commons.data.CountryData;
import org.ib.history.db.neo4j.Neo4jCountryService;
import org.ib.history.db.neo4j.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("backendService")
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

    @Autowired
    CountryService countryService;
//    Neo4jCountryService neo4jCountryService;

    @Override
    public List<CountryData> getCountries(String locale) {
        return countryService.getCountries();
    }

    @Override
    public void addCountry(CountryData country) {
        countryService.addCountry(country);
    }

    @Override
    public void deleteCountry(CountryData country) {
countryService.deleteCountry(country);
    }
}
