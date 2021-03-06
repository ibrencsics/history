package org.ib.history.client.utils;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Timer;
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

    private SuggestBox suggestBox;
    private T selected;

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
                        public void onSuccess(List<T> datas) {
                            SuggestOracle.Response r = new SuggestOracle.Response();
                            List<Suggestion> suggestions = new ArrayList<Suggestion>();

                            for (T data : datas) {
                                Suggestion suggestion = new Suggestion(data);
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

    public void setSuggestBox(SuggestBox suggestBox) {
        this.suggestBox = suggestBox;

        this.suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            @Override
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> suggestionSelectionEvent) {
                selected = ((Suggestion)suggestionSelectionEvent.getSelectedItem()).getSelection();
            }
        });
    }

    public void setSelected(T selected) {
        this.selected = selected;
        if (selected!=null)
            suggestBox.setText(displayString(selected));
    }

    public T getSelected() {
        return selected;
    }

    @Override
    public void requestSuggestions(Request request, Callback callback) {
        m_request = request;
        m_callback = callback;

        m_timer.cancel();
        m_timer.schedule(DELAY);
    }

    public abstract void setSuggestions(String pattern, org.ib.history.client.utils.AsyncCallback<List<T>> callback);
    public abstract String displayString(T selected);
    public abstract String replacementString(T selected);

    public class Suggestion/*<T extends AbstractData<T>>*/ implements SuggestOracle.Suggestion {

        private final T selection;

        public Suggestion(T selection) {
            this.selection = selection;
        }

        @Override
        public String getDisplayString() {
//            return selection.getName();
            return displayString(selection);
        }

        @Override
        public String getReplacementString() {
//            return selection.getName();
            return replacementString(selection);
        }

        public T getSelection() {
            return selection;
        }
    }
}
