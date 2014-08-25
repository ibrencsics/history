package org.ib.history.client.widget;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;

public interface Editor<T extends AbstractData<T>> extends IsWidget, HasText {
    void setSelected(T selected);
    void setPresenter(CrudPresenter<T> presenter);
    void save(T created);
    void hide();
}
