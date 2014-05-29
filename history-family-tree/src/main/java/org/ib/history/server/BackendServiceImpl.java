package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.Neo4jCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("backendService")
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

    @Autowired
    Neo4jCountryService neo4jCountryService;

    @Override
    public List<CountryDto> getCountries(String locale) {
        System.out.println("1");
        try {
            List<LocalizedDto<CountryDto>> countries = neo4jCountryService.getCountries();
            List<CountryDto> localeCountries = new ArrayList<>();

            for (LocalizedDto<CountryDto> country : countries) {
                if (locale==null || locale.equals("EN"))
                    localeCountries.add(country.getDefaultLocaleElement());
                else
                    localeCountries.add(country.getLocales().get(new Locale(locale)));
            }

            return localeCountries;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    @Override
    public void addCountry(CountryDto country) {
//        countryService.addCountry(country.getName());
    }

    @Override
    public void deleteCountry(CountryDto country) {
//        try {
//            countryService.deleteCountry(country);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }
}
