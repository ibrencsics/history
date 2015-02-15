package org.ib.history.client.views.welcome;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.WelcomePresenter;

public interface WelcomeView extends IsWidget {
    void setPresenter(WelcomePresenter presenter);
    HasWidgets getWorkspace();
}
