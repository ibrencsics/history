package org.ib.history.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.WelcomePresenter;

public interface WelcomeView extends IsWidget {
    void setPresenter(WelcomePresenter presenter);
}
