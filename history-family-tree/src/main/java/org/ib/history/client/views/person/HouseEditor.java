package org.ib.history.client.views.person;

import com.google.gwt.core.client.GWT;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.client.views.base.CustomEditor;
import org.ib.history.client.widget.FlexTableWrapper;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;

import java.util.ArrayList;
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

//        if (getSelected()!=null) {
//            ((PersonPresenter)getPresenter()).getHousesByIds(getSelected().getHouses(), new AsyncCallback<List<HouseData>>() {
//                @Override
//                public void onFailure(Throwable t) {
//                    GWT.log(t.getMessage());
//                }
//
//                @Override
//                public void onSuccess(List<HouseData> items) {
//                    for (HouseData house : items) {
//                        addRow(house);
//                    }
//                }
//            });
//        }
    }

    @Override
    protected void addRow() {

    }

    private void addRow(HouseData parent) {

    }
}
