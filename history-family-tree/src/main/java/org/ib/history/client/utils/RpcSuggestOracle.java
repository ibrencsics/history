package org.ib.history.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

public class RpcSuggestOracle extends SuggestOracle {

    private static final int DELAY = 1000;

    private SuggestOracle.Request m_request;
    private SuggestOracle.Callback m_callback;
    private Timer m_timer;

    private SuggestBox suggestBox;

    public RpcSuggestOracle() {

        m_timer = new Timer() {

            @Override
            public void run()
            {
                if (!suggestBox.getText().trim().isEmpty()) {
                    getSuggestions();
                }
            }
        };
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

    private void getSuggestions() {
        GWT.log("--- suggest");
    }
}
