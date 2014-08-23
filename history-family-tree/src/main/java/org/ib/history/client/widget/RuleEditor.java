package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.commons.data.RulerData;

public class RuleEditor extends Composite implements IsWidget, HasText {

    private static RuleEditorUiBinder uiBinder = GWT.create(RuleEditorUiBinder.class);

    interface RuleEditorUiBinder extends UiBinder<Widget, RuleEditor> {
    }

    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    Button btnAdd;

    private RulerData selectedRuler;


    public RuleEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

    public void setSelected(RulerData selectedRuler) {
        setText(selectedRuler.getName());
        this.selectedRuler = selectedRuler;
    }

    @UiHandler("btnAdd")
    public void addItem(ClickEvent clickEvent) {

    }
}
