package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;

public abstract class CustomItemEditor<T extends AbstractData<T>> extends Composite implements Editor<T> {

    private static CustomItemEditorUiBinder uiBinder = GWT.create(CustomItemEditorUiBinder.class);
    interface CustomItemEditorUiBinder extends UiBinder<Widget, CustomItemEditor> {}

    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    Button btnAdd;

    private T selectedItem;
    private CrudPresenter<T> presenter;
    protected FlexTableWrapper flexTableWrapper;

    public CustomItemEditor() {
        initWidget(uiBinder.createAndBindUi(this));
        visualize();
    }

    @Override
    public void setSelected(T selected) {
        this.selectedItem = selected;
        visualize();
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
    }

    public CrudPresenter<T> getPresenter() {
        return presenter;
    }

    @Override
    public void hide() {
        flexTable.removeAllRows();
        selectedItem = null;
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

    protected abstract void visualize();
    protected abstract void addRow();

    @UiHandler("btnAdd")
    public void addItem(ClickEvent clickEvent) {
        addRow();
    }
}
