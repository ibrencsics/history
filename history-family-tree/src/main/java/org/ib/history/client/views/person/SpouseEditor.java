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
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.SpouseData;
import org.ib.history.commons.data.GwtDateFormat;

import java.util.*;

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

            final List<SpouseData> normalized = normalizeSpouses(getSelected().getSpouses());
            List<PersonData> spouseIds = new ArrayList<PersonData>();
            for (SpouseData spouse : normalized) {
                spouseIds.add(spouse.getPerson2());
            }

            ((PersonPresenter)getPresenter()).getPersonsByIds(spouseIds, new AsyncCallback<List<PersonData>>() {
                @Override
                public void onFailure(Throwable t) {
                    Window.alert(t.getMessage());
                }

                @Override
                public void onSuccess(List<PersonData> persons) {
                    for (SpouseData spouse : normalizeSpouses(getSelected().getSpouses())) {
                        for (PersonData person : persons) {
                            if (spouse.getPerson2().getId().equals(person.getId())) {
                                spouse.setPerson2(person);
                                addRow(spouse);
                            }
                        }
                    }
                }
            });
        }
    }

    private List<SpouseData> normalizeSpouses(Set<SpouseData> spouses) {
        List<SpouseData> normalized = new ArrayList<>();

        for (SpouseData spouse : spouses) {
            if (spouse.getPerson1().getId().equals(getSelected().getId())) {
                normalized.add(spouse);
            } else {
                SpouseData reordered = new SpouseData.Builder()
                        .id(spouse.getId())
                        .from(spouse.getFrom()).to(spouse.getTo())
                        .person1(spouse.getPerson2()).person2(spouse.getPerson1())
                        .build();
                normalized.add(reordered);
            }
        }

        return normalized;
    }

    @Override
    protected void addRow() {
        addRow(new SpouseData.Builder().build());
    }

    private void addRow(SpouseData spouse) {
        List<Widget> row = new ArrayList<Widget>();

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
        SuggestBox sbSpouse = new SuggestBox(suggestOracle);
        sbSpouse.setWidth("300px");
        suggestOracle.setSuggestBox(sbSpouse);
        suggestOracle.setSelected(spouse.getPerson2());
        row.add(sbSpouse);

        TextBox tbFrom = new TextBox();
        tbFrom.setWidth("100px");
        tbFrom.setText(GwtDateFormat.convert(spouse.getFrom()));
        row.add(tbFrom);

        TextBox tbTo = new TextBox();
        tbTo.setWidth("100px");
        tbTo.setText(GwtDateFormat.convert(spouse.getTo()));
        row.add(tbTo);

        flexTableWrapper.addWidgetRowWithDelete(row);
    }

    private String personDisplayText(PersonData personData) {
        if (personData==null || personData.getName()==null  )
            return "";
        return personData.getName() +
                " (" + GwtDateFormat.convert(personData.getDateOfBirth()) + " - " + GwtDateFormat.convert(personData.getDateOfDeath()) + ")";
    }

    @Override
    public void save() {
        ((PersonPresenter)getPresenter()).setSpouses(getSelected(), getSpousesFromGUI(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Window.alert("Error when saving spouses");
            }

            @Override
            public void onSuccess(Void items) {
                getSelected().setSpouses(new HashSet<SpouseData>(getSpousesFromGUI()));
                GWT.log("Spouses saved");
            }
        });
    }

    private List<SpouseData> getSpousesFromGUI() {
        List<SpouseData> spouses = new ArrayList<SpouseData>();

        Iterator<List<? extends Widget>> iter = flexTableWrapper.iterator();
        while (iter.hasNext()) {
            List<? extends Widget> row = iter.next();

            SpouseData.Builder spouseDataBuilder = new SpouseData.Builder().person1(getSelected());

            SuggestBox sbSpouse = (SuggestBox) row.get(0);
            PersonData otherPerson = ((RpcSuggestOracle<PersonData>) sbSpouse.getSuggestOracle()).getSelected();
            spouseDataBuilder.person2(otherPerson);

            TextBox tbFrom = (TextBox) row.get(1);
            spouseDataBuilder.from(GwtDateFormat.convert(tbFrom.getText()));

            TextBox tbTo = (TextBox) row.get(2);
            spouseDataBuilder.to(GwtDateFormat.convert(tbTo.getText()));

            spouses.add(spouseDataBuilder.build());
        }

        return spouses;
    }
}
