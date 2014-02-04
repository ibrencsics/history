package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.db.neo4j.domain.CountryRepository;
import org.ib.history.db.neo4j.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("backendService")
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

    @Autowired
    CountryService countryService;

    @Override
    public List<String> getCountries() {

        return countryService.getCountries();

//        return Arrays.asList("asd", "sdf");
    }
}
