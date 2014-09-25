package org.ib.history.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ScreenChangeEventHandler extends EventHandler {
    void showCountry(ScreenChangeEvent event);
    void showHouse(ScreenChangeEvent event);
    void showPerson(ScreenChangeEvent event);
}
