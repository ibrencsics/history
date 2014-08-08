package org.ib.history.client.utils;

public interface AsyncCallback<T> {
    void onFailure(Throwable t);
    void onSuccess(T items);
}
