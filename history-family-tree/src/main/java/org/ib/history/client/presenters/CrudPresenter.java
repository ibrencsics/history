package org.ib.history.client.presenters;

import org.ib.history.commons.data.AbstractData;

public interface CrudPresenter<T extends AbstractData> extends Presenter {
    void addItem(T item);
    void deleteItem(T item);
    void setSelectedItem(T item);
}
