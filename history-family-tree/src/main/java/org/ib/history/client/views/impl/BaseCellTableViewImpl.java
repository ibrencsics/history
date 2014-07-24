package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.commons.data.AbstractData;
import org.ib.history.commons.data.PersonData;

public abstract class BaseCellTableViewImpl<T extends AbstractData> extends Composite {

    interface BaseCellTableUiBinder extends UiBinder<Widget, BaseCellTableViewImpl> {}
    private static BaseCellTableUiBinder uiBinder = GWT.create(BaseCellTableUiBinder.class);

    @UiField(provided = true)
    protected CellTable<T> cellTable;

    @UiField(provided = true)
    SimplePager pager;

    @UiField
    AbsolutePanel addItemPanel;

    protected final String COLUMN_NAME = "Name";
    protected final String COLUMN_EDIT = "Edit";
    protected final String COLUMN_DELETE = "Delete";

    protected TextColumn<T> columnName;
    protected Column<T, String> columnEdit;
    protected Column<T, String> columnDelete;
    protected Header<String> detailFooter;

    public BaseCellTableViewImpl() {
        cellTable = new CellTable<T>();
        // Create paging controls.
        pager = new SimplePager();
        pager.setDisplay(cellTable);

        initWidget(uiBinder.createAndBindUi(this));
//        cellTable.setSize("100%", "100%");
//        this.setSize("100%", "100%");

        buildTable();
        buildAddItemPanel();

        Window.enableScrolling(false);
    }

    private void buildTable() {

        // create common footer
//        detailFooter = new TextHeader("Details");

        // create columns
        buildColumns();
    }

    protected abstract void buildColumns();
    protected abstract void buildAddItemPanel();

    protected Header<String> buildHeader(final String text) {
        Header<String> head = new Header<String>(new TextCell()) {
            @Override
            public String getValue() {
                return text;
            }
        };
        return head;
    }

    protected void buildColumnName() {
        columnName = new TextColumn<T>() {
            @Override
            public String getValue(T object) {
                return object.getName();
            }
        };
        columnName.setDataStoreName(COLUMN_NAME);
    }

    protected void buildColumnEdit() {
        columnEdit = new Column<T, String>(new TextButtonCell()) {
            @Override
            public String getValue(T object) {
                return "Edit";
            }
        };
        columnEdit.setDataStoreName(COLUMN_EDIT);
        columnEdit.setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int index, T object, String value) {
                GWT.log(object.getName() + " pressed");
                onEdit(object);
            }
        });
    }

    protected void buildColumnDelete() {
        columnDelete = new Column<T, String>(new TextButtonCell()) {
            @Override
            public String getValue(T object) {
                return "Delete";
            }
        };
        columnDelete.setDataStoreName(COLUMN_DELETE);
        columnDelete.setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int index, T object, String value) {
                GWT.log(object.getName() + " pressed");
                onDelete(object);
            }
        });
    }


    protected abstract void onEdit(T data);
    protected abstract void onDelete(T data);

    protected void setAddItemForm(Widget addItemForm) {
        addItemPanel.add(addItemForm);
    }
}
