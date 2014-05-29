package org.ib.history.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.ib.history.commons.data.CountryDto;

import java.util.List;

//@RemoteServiceRelativePath("backend")
@RemoteServiceRelativePath("springGwtServices/backendService")
public interface BackendService extends RemoteService {
    List<CountryDto> getCountries(String locale);
    void addCountry(CountryDto country);
    void deleteCountry(CountryDto country);
}
