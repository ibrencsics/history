package org.ib.history.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.impl.PersonPresenterImpl;

public interface PersonView extends IsWidget {
    void setPresenter(PersonPresenterImpl presenter);
    void refreshGrid();
}
