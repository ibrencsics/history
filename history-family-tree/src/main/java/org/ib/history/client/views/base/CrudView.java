package org.ib.history.client.views.base;

import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;

public interface CrudView<T extends AbstractData> extends IsWidget {
    void setPresenter(CrudPresenter<T> presenter);
    void setSelectedItem(T selectedItem);
    void refreshGrid();
}
