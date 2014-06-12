package org.ib.history.client.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseCellTableViewImpl<T> extends Composite {

    interface BaseCellTableUiBinder extends UiBinder<Widget, BaseCellTableViewImpl> {}
    private static BaseCellTableUiBinder uiBinder = GWT.create(BaseCellTableUiBinder.class);

    @UiField(provided = true)
    protected CellTable<T> cellTable;

    @UiField(provided = true)
    SimplePager pager;

    Header<String> detailFooter;

    public BaseCellTableViewImpl() {
        cellTable = new CellTable<T>();
        // Create paging controls.
        pager = new SimplePager();
        pager.setDisplay(cellTable);

        initWidget(uiBinder.createAndBindUi(this));
        cellTable.setSize("100%", "100%");
        this.setSize("100%", "100%");

        buildTable();
        Window.enableScrolling(false);
    }

    private void buildTable() {

        // create common footer
        detailFooter = new TextHeader("Details");

        // create columns
        buildColumns();
    }

    protected abstract void buildColumns();
}
