package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.RulerPresenter;
import org.ib.history.commons.data.RulerData;

import java.util.ArrayList;
import java.util.List;

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
    protected RulerPresenter presenter;
    protected FlexTableWrapper flexTableWrapper;


    public RuleEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setPresenter(RulerPresenter presenter) {
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

    public void hide() {
        flexTable.removeAllRows();
        selectedRuler = null;
    }

    public void setSelected(RulerData selectedRuler) {
        this.selectedRuler = selectedRuler;
        visualize();
    }

    private void visualize() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(3);
        headers.add("From");
        headers.add("To");
        headers.add("Country");
        flexTableWrapper.addStringRow(headers);
    }

    @UiHandler("btnAdd")
    public void addItem(ClickEvent clickEvent) {

    }
}
