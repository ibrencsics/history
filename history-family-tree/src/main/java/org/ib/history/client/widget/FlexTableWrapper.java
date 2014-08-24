package org.ib.history.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlexTableWrapper {

    private int nextRow = 0;
    private final FlexTable flexTable;

    private List<List<? extends Widget>> rows = new ArrayList<List<? extends Widget>>();

    public FlexTableWrapper(FlexTable flexTable) {
        this.flexTable = flexTable;
        flexTable.clear();
    }

    protected void addCell(int x, int y, Widget widget) {
        flexTable.setWidget(x, y, widget);
    }

    protected synchronized void addStringRow(List<String> items) {
        List<Label> labels = new ArrayList<Label>();
        for (String item : items) {
            labels.add(new Label(item));
        }
        rows.add(labels);
        refresh();
    }

    protected synchronized void addWidgetRow(List<? extends Widget> widgets) {
        rows.add(widgets);
        refresh();
    }

    protected synchronized void addWidgetRowWithDelete(final List<? extends Widget> widgets) {
        Button btnDelete = new Button("Delete");
        btnDelete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
            }
        });

        List<Widget> row = new ArrayList<Widget>(widgets);
        row.add(btnDelete);
        rows.add(row);
        refresh();
    }

    protected synchronized List<Widget> getRow(int row) {
        List<Widget> widgets = new ArrayList<Widget>();

        for (int column=0; column<flexTable.getCellCount(row); column++) {
            widgets.add( flexTable.getWidget(row, column) );
        }

        return widgets;
    }

    private void refresh() {
        nextRow = 0;

        for (List<? extends Widget> row : rows) {
            int column=0;
            for (Widget widget : row) {
                addCell(nextRow, column++, widget);
            }
            nextRow++;
        }
    }

    public java.util.Iterator iterator() {
        return new Iterator();
    }

    public class Iterator implements java.util.Iterator<List<? extends Widget>> {
        int currentIndex = 1;

        @Override
        public boolean hasNext() {
            if (currentIndex >= rows.size()) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public List<? extends Widget> next() {
            return rows.get(currentIndex++);
        }

        @Override
        public void remove() {
            rows.remove(--currentIndex);
        }
    }
}
