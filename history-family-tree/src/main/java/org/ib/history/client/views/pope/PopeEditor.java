package org.ib.history.client.views.pope;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.client.views.base.Editor;
import org.ib.history.client.widget.FlexDateComponent;
import org.ib.history.commons.data.GwtDateFormat;
import org.ib.history.commons.data.PopeData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopeEditor extends BaseEditor<PopeData> {

    final String COLUMN_NUMBER = "Number";
    final String COLUMN_FROM = "From";
    final String COLUMN_TO = "To";

    @Override
    protected PopeData getEmptyItem() {
        return new PopeData.Builder().name("").number(null).from(null).to(null).build();
    }

    @Override
    protected List<String> getHeaders() {
        return Arrays.asList(COLUMN_NUMBER, COLUMN_FROM, COLUMN_TO);
    }

    @Override
    protected List<Widget> getDefaultLocaleWidgets() {
        List<Widget> widgets = new ArrayList<Widget>();

        TextBox tbNumber = new TextBox();
        if (selectedItem.getNumber()!=null)
            tbNumber.setText(selectedItem.getNumber() + "");
        widgets.add(tbNumber);

        FlexDateComponent fdFrom = new FlexDateComponent(selectedItem.getFrom());
        widgets.add(fdFrom);

        FlexDateComponent fdTo = new FlexDateComponent(selectedItem.getTo());
        widgets.add(fdTo);

        return widgets;
    }

    @Override
    protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
        List<Widget> widgets = new ArrayList<Widget>();

        return widgets;
    }

    @Override
    protected List<? extends Editor<PopeData>> getCustomEditors() {
        return new ArrayList<Editor<PopeData>>();
    }

    @Override
    protected void updateDefaultLocale(List<Widget> widgets) {
        TextBox tbNumber = (TextBox) widgets.get(0);
        if (tbNumber.getText() != null && !tbNumber.getText().equals(""))
            selectedItem.setNumber(Integer.parseInt(tbNumber.getText()));

        FlexDateComponent fdFrom = (FlexDateComponent) widgets.get(1);
        selectedItem.setFrom(fdFrom.getValue());

        FlexDateComponent fdTo = (FlexDateComponent) widgets.get(2);
        selectedItem.setTo(fdTo.getValue());
    }

    @Override
    protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {

    }
}
