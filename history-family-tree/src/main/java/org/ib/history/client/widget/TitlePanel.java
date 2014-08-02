package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

// http://www.wortzwei.de/blogzwei/2010/12/gwt-mit-dem-uibinder-eigene-widgets-entwickeln/
public class TitlePanel extends Composite implements IsWidget, HasWidgets, HasText {

    private static TitlePanelUiBinder uiBinder = GWT.create(TitlePanelUiBinder.class);

    @UiField
    FlowPanel bodyPanel;

    @UiField
    Label title;

    interface TitlePanelUiBinder extends UiBinder<Widget, TitlePanel> {
    }

    public TitlePanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void add(Widget w) {
        bodyPanel.add(w);
    }

    @Override
    public void clear() {
        bodyPanel.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return bodyPanel.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return bodyPanel.remove(w);
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

}