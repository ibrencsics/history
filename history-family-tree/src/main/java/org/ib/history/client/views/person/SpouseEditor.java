package org.ib.history.client.views.person;

import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.client.views.base.CustomEditor;
import org.ib.history.client.widget.FlexTableWrapper;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.SpouseData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.List;

public class SpouseEditor extends CustomEditor<PersonData> {

    protected FlexTableWrapper flexTableWrapper;

    @Override
    protected void show() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(3);
        headers.add("Spouse");
        headers.add("From");
        headers.add("To");
        flexTableWrapper.addStringRow(headers);

        if (getSelected()!=null) {
            for (SpouseData spouse : getSelected().getSpouses()) {
                addRow(spouse);
            }
        }
    }

    @Override
    protected void addRow() {
        addRow(new SpouseData.Builder().build());
    }

    private void addRow(SpouseData spouse) {
        List<Widget> ruleRow = new ArrayList<Widget>();

        RpcSuggestOracle suggestOracle = new RpcSuggestOracle<PersonData>() {
            @Override
            public void setSuggestions(String pattern, AsyncCallback<List<PersonData>> callback) {
                ((PersonPresenter)getPresenter()).setPersonSuggestions(pattern, callback);
            }

            @Override
            public String displayString(PersonData selected) {
                return personDisplayText(selected);
            }

            @Override
            public String replacementString(PersonData selected) {
                return personDisplayText(selected);
            }
        };
        SuggestBox sbHouse = new SuggestBox(suggestOracle);
        sbHouse.setWidth("300px");
        suggestOracle.setSuggestBox(sbHouse);
        suggestOracle.setSelected(spouse.getPerson2());
        ruleRow.add(sbHouse);

        TextBox tbFrom = new TextBox();
        tbFrom.setText(GwtDateFormat.convert(spouse.getFrom()));
        ruleRow.add(tbFrom);

        TextBox tbTo = new TextBox();
        tbTo.setText(GwtDateFormat.convert(spouse.getTo()));
        ruleRow.add(tbTo);

        flexTableWrapper.addWidgetRowWithDelete(ruleRow);
    }

    private String personDisplayText(PersonData personData) {
        if (personData==null || personData.getName()==null  )
            return "";
        return personData.getName() +
                " (" + GwtDateFormat.convert(personData.getDateOfBirth()) + "-" + GwtDateFormat.convert(personData.getDateOfDeath()) + ")";
    }

    @Override
    public void save() {

    }
}
