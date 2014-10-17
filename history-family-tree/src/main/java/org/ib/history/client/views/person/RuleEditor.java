package org.ib.history.client.views.person;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.client.views.base.CustomEditor;
import org.ib.history.client.widget.FlexTableWrapper;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulesData;
import org.ib.history.commons.data.GwtDateFormat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RuleEditor extends CustomEditor<PersonData> {

    protected FlexTableWrapper flexTableWrapper;

    @Override
    protected void show() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(3);
        headers.add("Country");
        headers.add("Title");
        headers.add("Number");
        headers.add("From");
        headers.add("To");
        flexTableWrapper.addStringRow(headers);

        if (getSelected()!=null) {
            List<CountryData> countryIds = new ArrayList<CountryData>();
            for (RulesData rules : getSelected().getRules()) {
                countryIds.add(rules.getCountry());
            }

            ((PersonPresenter)getPresenter()).getCountriesByIds(countryIds, new AsyncCallback<List<CountryData>>() {
                @Override
                public void onFailure(Throwable t) {
                    Window.alert(t.getMessage());
                }

                @Override
                public void onSuccess(List<CountryData> countries) {
                    for (RulesData rules : getSelected().getRules()) {
                        for (CountryData country : countries) {
                            if (rules.getCountry().getId().equals(country.getId())) {
                                rules.setCountry(country);
                                addRow(rules);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void addRow() {
        addRow(new RulesData.Builder().build());
    }

    private void addRow(RulesData rules) {
        List<Widget> row = new ArrayList<Widget>();

        RpcSuggestOracle suggestOracle = new RpcSuggestOracle<CountryData>() {
            @Override
            public void setSuggestions(String pattern, AsyncCallback<List<CountryData>> callback) {
                ((PersonPresenter)getPresenter()).setCountrySuggestions(pattern, callback);
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
        sbCountry.setWidth("200px");
        suggestOracle.setSuggestBox(sbCountry);
        suggestOracle.setSelected(rules.getCountry());
        row.add(sbCountry);

        TextBox tbTitle = new TextBox();
        tbTitle.setWidth("100px");
        tbTitle.setText(rules.getTitle());
        row.add(tbTitle);

        TextBox tbNumber = new TextBox();
        tbNumber.setWidth("50px");
        if (rules.getNumber()!=null)
            tbNumber.setText(rules.getNumber() + "");
        row.add(tbNumber);

        TextBox tbFrom = new TextBox();
        tbFrom.setWidth("100px");
        tbFrom.setText(GwtDateFormat.convert(rules.getFrom()));
        row.add(tbFrom);

        TextBox tbTo = new TextBox();
        tbTo.setWidth("100px");
        tbTo.setText(GwtDateFormat.convert(rules.getTo()));
        row.add(tbTo);

        flexTableWrapper.addWidgetRowWithDelete(row);
    }

    @Override
    public void save() {
        ((PersonPresenter)getPresenter()).setRules(getSelected(), getRulesFromGUI(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Window.alert("Error when saving rules");
            }

            @Override
            public void onSuccess(Void items) {
                getSelected().setRules(new HashSet<RulesData>(getRulesFromGUI()));
                GWT.log("Rules saved");
            }
        });
    }

    private List<RulesData> getRulesFromGUI() {
        List<RulesData> rules = new ArrayList<RulesData>();

        Iterator<List<? extends Widget>> iter = flexTableWrapper.iterator();
        while (iter.hasNext()) {
            List<? extends Widget> row = iter.next();

            RulesData.Builder rulesDataBuilder = new RulesData.Builder().person(getSelected());

            SuggestBox sbCountry = (SuggestBox) row.get(0);
            CountryData country = ((RpcSuggestOracle<CountryData>) sbCountry.getSuggestOracle()).getSelected();
            rulesDataBuilder.country(country);

            TextBox tbTitle = (TextBox) row.get(1);
            rulesDataBuilder.title(tbTitle.getText());

            TextBox tbNumber = (TextBox) row.get(2);
            if (tbNumber.getText() != null && !tbNumber.getText().equals(""))
                rulesDataBuilder.number(Integer.valueOf(tbNumber.getText()));

            TextBox tbFrom = (TextBox) row.get(3);
            rulesDataBuilder.from(GwtDateFormat.convert(tbFrom.getText()));

            TextBox tbTo = (TextBox) row.get(4);
            rulesDataBuilder.to(GwtDateFormat.convert(tbTo.getText()));

            rules.add(rulesDataBuilder.build());
        }

        return rules;
    }
}
