package org.ib.history.client.widget;

import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.RulerPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.RulerData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RuleEditor extends CustomItemEditor<RulerData> {


    public RuleEditor() {
        super();
    }

    @Override
    protected void visualize() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(3);
        headers.add("From");
        headers.add("To");
        headers.add("Country");
        flexTableWrapper.addStringRow(headers);

        if (getSelectedItem()!=null) {
            for (RulerData.RulesData rules : getSelectedItem().getRules()) {
                addRow(rules);
            }
        }
    }

    @Override
    protected void addRow() {
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
                ((RulerPresenter)getPresenter()).setCountrySuggestions(pattern, callback);
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
    public void save() {
        //To change body of implemented methods use File | Settings | File Templates.
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
