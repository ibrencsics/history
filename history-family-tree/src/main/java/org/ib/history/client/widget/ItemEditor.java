package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.utils.SupportedLocales;
import org.ib.history.commons.data.AbstractData;

import java.util.Iterator;

public abstract class ItemEditor<T extends AbstractData<T>> extends Composite implements IsWidget, HasText {

    private static ItemEditorUiBinder uiBinder = GWT.create(ItemEditorUiBinder.class);

    interface ItemEditorUiBinder extends UiBinder<Widget, ItemEditor> {
    }

    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    Button btnNew;

    @UiField
    Button btnSave;

    protected T selectedItem;
    protected CrudPresenter<T> presenter;

    public ItemEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        visualize();
    }

    public void createNewItem(T emptyNewItem) {
        this.selectedItem = emptyNewItem;
        visualize();
    }

    private void visualize() {
        TextBox textBox = new TextBox();

        flexTable.clear();
        flexTable.setWidget(0, 0, new Label("Lang"));
        flexTable.setWidget(0, 1, new Label("Name"));

        SupportedLocales defaultLocale = SupportedLocales.getDefault();
        flexTable.setWidget(1, 0, new Label(defaultLocale.name()));
        textBox.setText(selectedItem.getName());
        flexTable.setWidget(1, 1, textBox);

        visualizeOthers();
    }

    protected abstract void visualizeOthers();

    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

    @UiHandler("btnNew")
    public void newItem(ClickEvent clickEvent) {

    }

    @UiHandler("btnSave")
    public void saveItem(ClickEvent clickEvent) {

    }
}