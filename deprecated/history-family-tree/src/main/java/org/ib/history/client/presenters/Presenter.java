package org.ib.history.client.presenters;

import com.google.gwt.user.client.ui.HasWidgets;

public interface Presenter {
    void go(final HasWidgets container);
    void bind();
}
