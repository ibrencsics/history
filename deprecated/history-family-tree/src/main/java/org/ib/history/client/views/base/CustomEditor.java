package org.ib.history.client.views.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;

public abstract class CustomEditor<T extends AbstractData<T>> extends Composite implements Editor<T> {

    private static CustomEditorUiBinder uiBinder = GWT.create(CustomEditorUiBinder.class);
    interface CustomEditorUiBinder extends UiBinder<Widget, CustomEditor> {}

    interface Style extends CssResource {
        String clean();
        String dirty();
    }

    @UiField
    Style style;


    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    Button btnAdd;

    private T selected;
    private CrudPresenter<T> presenter;

    private boolean dirty=false;

    public CustomEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSelected(T selected) {
        this.selected = selected;
        setClean();
        show();
    }

    public T getSelected() {
        return selected;
    }

    @Override
    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
    }

    public CrudPresenter<T> getPresenter() {
        return presenter;
    }

    @Override
    public void save() {

    }

    @Override
    public void save(T created) {

    }

    @Override
    public void hide() {

    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }


    protected abstract void show();
    protected abstract void addRow();

    @UiHandler("btnAdd")
    public void addItem(ClickEvent clickEvent) {
        addRow();
        setDirty();
    }

    @UiHandler("btnSave")
    public void save(ClickEvent clickEvent) {
        save();
        setClean();
    }

    protected void setDirty() {
        title.removeStyleName(style.clean());
        title.addStyleName(style.dirty());
    }

    protected void setClean() {
        title.removeStyleName(style.dirty());
        title.addStyleName(style.clean());
    }
}
