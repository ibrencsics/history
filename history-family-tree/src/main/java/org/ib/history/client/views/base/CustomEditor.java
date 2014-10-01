package org.ib.history.client.views.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;

public class CustomEditor<T extends AbstractData<T>> extends Composite implements Editor<T> {

    private static CustomEditorUiBinder uiBinder = GWT.create(CustomEditorUiBinder.class);
    interface CustomEditorUiBinder extends UiBinder<Widget, CustomEditor> {}

    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    Button btnAdd;


    public CustomEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setSelected(T selected) {

    }

    @Override
    public void setPresenter(CrudPresenter<T> presenter) {

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
}
