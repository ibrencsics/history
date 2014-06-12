package org.ib.history.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ScreenChangeEvent extends GwtEvent<ScreenChangeEventHandler> {

    public static Type<ScreenChangeEventHandler> TYPE = new Type<ScreenChangeEventHandler>();

    public enum Screen { COUNTRY_LIST, COUNTRY_ADD, PERSON_LIST, PERSON_ADD }

    private final Screen screen;

    public ScreenChangeEvent(Screen screen) {
        this.screen = screen;
    }

    @Override
    public Type<ScreenChangeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ScreenChangeEventHandler screenChangeEventHandler) {
        switch (screen) {
            case COUNTRY_LIST:
                screenChangeEventHandler.showCountryList(this);
                break;
            case COUNTRY_ADD:
                screenChangeEventHandler.showCountryAdd(this);
                break;
            case PERSON_LIST:
                screenChangeEventHandler.showPersonList(this);
                break;
            case PERSON_ADD:
                screenChangeEventHandler.showPersonAdd(this);
                break;
        }
    }
}
