package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.commons.data.FlexibleDate;

public class FlexDateComponent extends Composite {

    private static FlexDateComponentUiBinder uiBinder = GWT.create(FlexDateComponentUiBinder.class);
    interface FlexDateComponentUiBinder extends UiBinder<Widget, FlexDateComponent> {}

    @UiField
    CheckBox cbCirca;

    @UiField
    CheckBox cbAd;

    @UiField
    TextBox tbYear;

    @UiField
    TextBox tbMonth;

    @UiField
    TextBox tbDay;


    public FlexDateComponent(FlexibleDate flexDate) {
        initWidget(uiBinder.createAndBindUi(this));

        tbYear.setMaxLength(4);
        tbMonth.setMaxLength(2);
        tbDay.setMaxLength(2);

        if (flexDate!=null)
            initValues(flexDate);
    }

    private void initValues(FlexibleDate flexDate) {
        if (flexDate.isCirca()) cbCirca.setValue(true);
        if (flexDate.isAD()) cbAd.setValue(true);

        tbYear.setText(flexDate.getYear()+"");

        if (flexDate.isThereMonth()) {
            tbMonth.setText(flexDate.getMonth()+"");
        }

        if (flexDate.isThereDay()) {
            tbDay.setText(flexDate.getDay()+"");
        }
    }

    public FlexibleDate getValue() {
        FlexibleDate flexDate = new FlexibleDate();

        flexDate.setCirca(cbCirca.getValue() ? true : false);
        flexDate.setAD(cbAd.getValue() ? true : false);

        flexDate.setYear(Integer.valueOf(tbYear.getText()));

        if (!tbMonth.getText().equals("")) {
            flexDate.setMonth(Integer.valueOf(tbMonth.getText()));
            flexDate.setThereMonth(true);
        } else {
            flexDate.setThereMonth(false);
        }

        if (!tbDay.getText().equals("")) {
            flexDate.setDay(Integer.valueOf(tbDay.getText()));
            flexDate.setThereDay(true);
        } else {
            flexDate.setThereDay(false);
        }


        return flexDate;
    }
}
