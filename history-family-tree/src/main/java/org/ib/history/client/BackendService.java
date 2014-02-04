package org.ib.history.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

//@RemoteServiceRelativePath("backend")
@RemoteServiceRelativePath("springGwtServices/backendService")
public interface BackendService extends RemoteService {
    List<String> getCountries();
}
