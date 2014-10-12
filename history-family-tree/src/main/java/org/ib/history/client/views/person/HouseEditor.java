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
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HouseEditor extends CustomEditor<PersonData> {

    protected FlexTableWrapper flexTableWrapper;

    @Override
    protected void show() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(1);
        headers.add("House");
        flexTableWrapper.addStringRow(headers);

        if (getSelected()!=null) {
            ((PersonPresenter)getPresenter()).getHousesByIds(getSelected().getHouses(), new AsyncCallback<List<HouseData>>() {
                @Override
                public void onFailure(Throwable t) {
                    GWT.log(t.getMessage());
                }

                @Override
                public void onSuccess(List<HouseData> items) {
                    for (HouseData house : items) {
                        addRow(house);
                    }
                }
            });
        }
    }

    @Override
    protected void addRow() {
        addRow(new HouseData.Builder().build());
    }

    private void addRow(HouseData parent) {
        List<Widget> ruleRow = new ArrayList<Widget>();

        RpcSuggestOracle suggestOracle = new RpcSuggestOracle<HouseData>() {
            @Override
            public void setSuggestions(String pattern, AsyncCallback<List<HouseData>> callback) {
                ((PersonPresenter)getPresenter()).setHouseSuggestions(pattern, callback);
            }

            @Override
            public String displayString(HouseData selected) {
                return personDisplayText(selected);
            }

            @Override
            public String replacementString(HouseData selected) {
                return personDisplayText(selected);
            }
        };
        SuggestBox sbHouse = new SuggestBox(suggestOracle);
        sbHouse.setWidth("300px");
        suggestOracle.setSuggestBox(sbHouse);
        suggestOracle.setSelected(parent);
        ruleRow.add(sbHouse);

        flexTableWrapper.addWidgetRowWithDelete(ruleRow);
    }

    private String personDisplayText(HouseData houseData) {
        if (houseData==null || houseData.getName()==null  )
            return "";
        return houseData.getName();
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
}
