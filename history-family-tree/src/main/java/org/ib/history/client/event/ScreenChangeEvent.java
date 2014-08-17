package org.ib.history.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ScreenChangeEvent extends GwtEvent<ScreenChangeEventHandler> {

    public static Type<ScreenChangeEventHandler> TYPE = new Type<ScreenChangeEventHandler>();

    public enum Screen {COUNTRY, HOUSE, PERSON, RULER}

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
            case COUNTRY:
                screenChangeEventHandler.showCountry(this);
                break;
            case HOUSE:
                screenChangeEventHandler.showHouse(this);
                break;
            case PERSON:
                screenChangeEventHandler.showPerson(this);
                break;
            case RULER:
                screenChangeEventHandler.showRuler(this);
        }
    }
}
