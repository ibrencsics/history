package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.presenters.RulerPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RuleEditor extends Composite implements Editor<RulerData> {

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
        visualize();
    }

    @Override
    public void setPresenter(CrudPresenter<RulerData> presenter) {
        this.presenter = (RulerPresenter) presenter;
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

    @Override
    public void hide() {
        flexTable.removeAllRows();
        selectedRuler = null;
    }

    @Override
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

        if (selectedRuler!=null) {
            for (RulerData.RulesData rules : selectedRuler.getRules()) {
                addRow(rules);
            }
        }
    }

    @UiHandler("btnAdd")
    public void addItem(ClickEvent clickEvent) {
        addRow(new RulerData.RulesData.Builder().build());
    }

    private void addRow(RulerData.RulesData rules) {
        List<Widget> ruleRow = new ArrayList<Widget>();

        TextBox tbFrom = new TextBox();
        tbFrom.setText(GwtDateFormat.convert(rules.getFrom()));
        ruleRow.add(tbFrom);

        TextBox tbTo = new TextBox();
        tbTo.setText(GwtDateFormat.convert(rules.getTo()));
        ruleRow.add(tbTo);

        RpcSuggestOracle suggestOracle = new RpcSuggestOracle<CountryData>() {
            @Override
            public void setSuggestions(String pattern, AsyncCallback<List<CountryData>> callback) {
                presenter.setCountrySuggestions(pattern, callback);
            }

            @Override
            public String displayString(CountryData selected) {
                return selected.getName();
            }

            @Override
            public String replacementString(CountryData selected) {
                return selected.getName();
            }
        };
        SuggestBox sbCountry = new SuggestBox(suggestOracle);
        suggestOracle.setSuggestBox(sbCountry);
        suggestOracle.setSelected(rules.getCountry());
        ruleRow.add(sbCountry);

        flexTableWrapper.addWidgetRowWithDelete(ruleRow);
    }

    @Override
    public void save(RulerData created) {
        List<RulerData.RulesData> rules = new ArrayList<RulerData.RulesData>();

        Iterator<List<? extends Widget>> iter = flexTableWrapper.iterator();
        while (iter.hasNext()) {
            List<? extends Widget> row = iter.next();

            SuggestBox sbCountry = ((SuggestBox)row.get(2));
            CountryData country = ((RpcSuggestOracle<CountryData>) sbCountry.getSuggestOracle()).getSelected();

            RulerData.RulesData.Builder builder = new RulerData.RulesData.Builder()
                    .from(GwtDateFormat.convert( ((TextBox)row.get(0)).getText() ))
                    .to(GwtDateFormat.convert( ((TextBox)row.get(1)).getText() ))
                    .country(country);

            rules.add(builder.build());
        }

        created.setRules(new HashSet<RulerData.RulesData>(rules));
    }
}
