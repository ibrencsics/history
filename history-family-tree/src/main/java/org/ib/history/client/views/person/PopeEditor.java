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
import org.ib.history.commons.data.PopeData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PopeEditor extends CustomEditor<PersonData> {

    protected FlexTableWrapper flexTableWrapper;

    @Override
    protected void show() {
        flexTableWrapper = new FlexTableWrapper(flexTable);

        // headers
        List<String> headers = new ArrayList<String>(1);
        headers.add("Pope");
        flexTableWrapper.addStringRow(headers);

        if (getSelected()!=null && getSelected().getPope()!=null) {
            ((PersonPresenter)getPresenter()).getPopeByIds(getSelected().getPope(), new AsyncCallback<PopeData>() {
                @Override
                public void onFailure(Throwable t) {
                    GWT.log(t.getMessage());
                }

                @Override
                public void onSuccess(PopeData popeData) {
                    addRow(popeData);
                }
            });
        }
    }

    @Override
    protected void addRow() {
        addRow(new PopeData.Builder().build());
    }

    private void addRow(PopeData popeData) {
        List<Widget> ruleRow = new ArrayList<Widget>();

        RpcSuggestOracle suggestOracle = new RpcSuggestOracle<PopeData>() {
            @Override
            public void setSuggestions(String pattern, AsyncCallback<List<PopeData>> callback) {
                ((PersonPresenter)getPresenter()).setPopeSuggestions(pattern, callback);
            }

            @Override
            public String displayString(PopeData selected) {
                return personDisplayText(selected);
            }

            @Override
            public String replacementString(PopeData selected) {
                return personDisplayText(selected);
            }
        };
        SuggestBox sbPope = new SuggestBox(suggestOracle);
        sbPope.setWidth("200px");
        suggestOracle.setSuggestBox(sbPope);
        suggestOracle.setSelected(popeData);
        ruleRow.add(sbPope);

        flexTableWrapper.addWidgetRowWithDelete(ruleRow);
    }

    private String personDisplayText(PopeData selected) {
        return selected.getNumber() + ". " + selected.getName();
    }

    @Override
    public void save() {
        PopeData pope = getPopeFromGUI();

        if (pope!=null) {
            ((PersonPresenter) getPresenter()).setPope(getSelected(), pope, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    Window.alert("Error when saving pope");
                }

                @Override
                public void onSuccess(Void items) {
                    getSelected().setPope(getPopeFromGUI());
                }
            });
        }
    }

    private PopeData getPopeFromGUI() {
        Iterator<List<? extends Widget>> iter = flexTableWrapper.iterator();
        if (iter.hasNext()) {
            List<? extends Widget> row = iter.next();

            SuggestBox sbPope = ((SuggestBox)row.get(0));
            PopeData pope = ((RpcSuggestOracle<PopeData>) sbPope.getSuggestOracle()).getSelected();
            return pope;
        }

        return null;
    }
}
