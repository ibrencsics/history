package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("backendService")
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

    @Autowired
    CountryService countryService;

    @Override
    public List<CountryDto> getCountries() {
        return countryService.getCountries();
    }

    @Override
    public void addCountry(CountryDto country) {
        countryService.addCountry(country.getName());
    }

    @Override
    public void deleteCountry(CountryDto country) {
        try {
            countryService.deleteCountry(country);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
