package org.ib.history.client.widget;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class FlexTableWrapper {

    private int nextRow = 0;
    private final FlexTable flexTable;

    public FlexTableWrapper(FlexTable flexTable) {
        this.flexTable = flexTable;
        flexTable.clear();
    }

    protected void addCell(int x, int y, Widget widget) {
        flexTable.setWidget(x, y, widget);
    }

    protected void addStringRow(List<String> items) {
        List<Label> labels = new ArrayList<Label>();
        for (String item : items) {
            labels.add(new Label(item));
        }
        addWidgetRow(labels);
    }

    protected void addWidgetRow(List<? extends Widget> widgets) {
        int column=0;
        for (Widget widget : widgets) {
            addCell(nextRow, column++, widget);
        }
        nextRow++;
    }

    protected List<Widget> getRow(int row) {
        List<Widget> widgets = new ArrayList<Widget>();

        for (int column=0; column<flexTable.getCellCount(row); column++) {
            widgets.add( flexTable.getWidget(row, column) );
        }

        return widgets;
    }
}
