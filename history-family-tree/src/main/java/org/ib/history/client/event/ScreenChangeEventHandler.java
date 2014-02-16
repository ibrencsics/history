package org.ib.history.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ScreenChangeEventHandler extends EventHandler {
    void showCountryList(ScreenChangeEvent event);
    void showCountryAdd(ScreenChangeEvent event);
}
