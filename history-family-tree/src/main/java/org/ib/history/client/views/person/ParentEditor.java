package org.ib.history.client.views.person;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.utils.RpcSuggestOracle;
import org.ib.history.client.views.base.CustomEditor;
import org.ib.history.client.widget.FlexTableWrapper;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParentEditor extends CustomEditor<PersonData> {

    protected FlexTableWrapper flexTableWrapper;

    @Override
    protected void show() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(1);
        headers.add("Parent");
        flexTableWrapper.addStringRow(headers);

        if (getSelected()!=null) {
            ((PersonPresenter)getPresenter()).getPersonsByIds(getSelected().getParents(), new AsyncCallback<List<PersonData>>() {
                @Override
                public void onFailure(Throwable t) {
                    GWT.log(t.getMessage());
                }

                @Override
                public void onSuccess(List<PersonData> items) {
                    for (PersonData parent : items) {
                        addRow(parent);
                    }
                }
            });
        }
    }

    @Override
    protected void addRow() {
        addRow(new PersonData.Builder().build());
    }

    private void addRow(PersonData parent) {
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
        SuggestBox sbPerson = new SuggestBox(suggestOracle);
        sbPerson.setWidth("300px");
        suggestOracle.setSuggestBox(sbPerson);
        suggestOracle.setSelected(parent);
        ruleRow.add(sbPerson);

        flexTableWrapper.addWidgetRowWithDelete(ruleRow);
    }

    private String personDisplayText(PersonData personData) {
        if (personData==null || personData.getName()==null  )
            return "";
        return personData.getName() +
                " (" + GwtDateFormat.convert(personData.getDateOfBirth()) + " - " + GwtDateFormat.convert(personData.getDateOfDeath()) + ")";
    }

    @Override
    public void save() {
        ((PersonPresenter)getPresenter()).setParents(getSelected(), getParentsFromGUI(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Window.alert("Error when saving parents");
            }

            @Override
            public void onSuccess(Void items) {
                getSelected().setParents(getParentsFromGUI());
            }
        });
    }

    private List<PersonData> getParentsFromGUI() {
        List<PersonData> parents = new ArrayList<PersonData>();

        Iterator<List<? extends Widget>> iter = flexTableWrapper.iterator();
        while (iter.hasNext()) {
            List<? extends Widget> row = iter.next();

            SuggestBox sbParent = ((SuggestBox)row.get(0));
            PersonData parent = ((RpcSuggestOracle<PersonData>) sbParent.getSuggestOracle()).getSelected();
            parents.add(parent);
        }

        return parents;
    }

    // not used
    @Override
    public void save(PersonData created) {
        List<PersonData> parents = new ArrayList<PersonData>();

        Iterator<List<? extends Widget>> iter = flexTableWrapper.iterator();
        while (iter.hasNext()) {
            List<? extends Widget> row = iter.next();

            SuggestBox sbParent = ((SuggestBox)row.get(0));
            PersonData parent = ((RpcSuggestOracle<PersonData>) sbParent.getSuggestOracle()).getSelected();
            parents.add(parent);
        }

        created.setParents(parents);
    }
}
