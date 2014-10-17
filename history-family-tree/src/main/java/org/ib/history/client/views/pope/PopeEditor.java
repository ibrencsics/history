package org.ib.history.client.views.pope;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.client.views.base.Editor;
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

    }

    @Override
    protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {

    }
}
