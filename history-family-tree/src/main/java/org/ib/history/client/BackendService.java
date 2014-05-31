package org.ib.history.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.ib.history.commons.data.CountryData;

import java.util.List;

//@RemoteServiceRelativePath("backend")
@RemoteServiceRelativePath("springGwtServices/backendService")
public interface BackendService extends RemoteService {
    List<CountryData> getCountries(String locale);
    void addCountry(CountryData country);
    void deleteCountry(CountryData country);
}
