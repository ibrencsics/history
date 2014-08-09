package org.ib.history.client.utils;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.commons.data.AbstractData;

import java.util.ArrayList;
import java.util.List;

public abstract class RpcSuggestOracle<T extends AbstractData<T>> extends SuggestOracle {

    private static final int DELAY = 1000;

    private SuggestOracle.Request m_request;
    private SuggestOracle.Callback m_callback;
    private Timer m_timer;
    private BackendServiceAsync backendService;

    private SuggestBox suggestBox;

    public RpcSuggestOracle() {
        m_timer = new Timer() {

            @Override
            public void run()
            {
                if (!suggestBox.getText().trim().isEmpty()) {
                    setSuggestions(suggestBox.getText(), new org.ib.history.client.utils.AsyncCallback<List<T>>() {
                        @Override
                        public void onFailure(Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(List<T> houseDatas) {
                            SuggestOracle.Response r = new SuggestOracle.Response();
                            List<Suggestion> suggestions = new ArrayList<Suggestion>();

                            for (T houseData : houseDatas) {
                                Suggestion suggestion = new MultiWordSuggestOracle.MultiWordSuggestion(houseData.getName(), houseData.getName());
                                suggestions.add(suggestion);
                            }

                            r.setSuggestions(suggestions);
                            m_callback.onSuggestionsReady(m_request, r);
                        }
                    });
                }
            }
        };
    }

    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }

    public void setSuggestBox(SuggestBox suggestBox) {
        this.suggestBox = suggestBox;
    }

    @Override
    public void requestSuggestions(Request request, Callback callback) {
        m_request = request;
        m_callback = callback;

        m_timer.cancel();
        m_timer.schedule(DELAY);
    }

    public abstract void setSuggestions(String pattern, org.ib.history.client.utils.AsyncCallback<List<T>> callback);

//    private void getSuggestions() {
//        GWT.log("--- suggest");
//
//        backendService.getHouses(new AsyncCallback<List<HouseData>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(List<HouseData> houseDatas) {
//                SuggestOracle.Response r = new SuggestOracle.Response();
//                List<Suggestion> suggestions = new ArrayList<Suggestion>();
//
//                for (HouseData houseData : houseDatas) {
//                    Suggestion suggestion = new MultiWordSuggestOracle.MultiWordSuggestion(houseData.getName(), houseData.getName());
//                    suggestions.add(suggestion);
//                }
//
//                r.setSuggestions(suggestions);
//                m_callback.onSuggestionsReady(m_request, r);
//            }
//        });
//    }
}
