package org.ib.history.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.SuggestOracle;

public class PersonSuggestOracle extends SuggestOracle {

    @Override
    public void requestSuggestions(Request request, Callback callback) {
        GWT.log(request.getQuery());
    }
}
