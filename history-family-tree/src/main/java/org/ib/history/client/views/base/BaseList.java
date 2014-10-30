package org.ib.history.client.views.base;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;

public abstract class BaseList<T extends AbstractData<T>> {

    protected final String COLUMN_NAME = "Name";
    protected final String COLUMN_EDIT = "Edit";
    protected final String COLUMN_DELETE = "Delete";

    protected final CellTable<T> ctList;
    protected CrudPresenter<T> presenter;

    protected BaseList() {
        ctList = new CellTable<T>();
        buildListTable();
    }


    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
    }

    public CellTable<T> getCtList() {
        return ctList;
    }

    /**
     * GUI
     */

    private void buildListTable() {
        ctList.addColumn(buildColumnName(), buildHeader(COLUMN_NAME));
        buildListColumns();
        ctList.addColumn(buildColumnEdit(), buildHeader(COLUMN_EDIT));
        ctList.addColumn(buildColumnDelete(), buildHeader(COLUMN_DELETE));
    }

    protected abstract void buildListColumns();


    protected Header<String> buildHeader(final String text) {
        Header<String> head = new Header<String>(new TextCell()) {
            @Override
            public String getValue() {
                return text;
            }
        };
        return head;
    }

    /**
     * List
     */

    protected TextColumn<T> buildColumnName() {
        TextColumn<T> columnName = new TextColumn<T>() {
            @Override
            public String getValue(T object) {
                return object.getName();
            }
        };
        columnName.setDataStoreName(COLUMN_NAME);
        return columnName;
    }

    protected Column<T, String> buildColumnEdit() {
        Column<T, String> columnEdit = new Column<T, String>(new TextButtonCell()) {
            @Override
            public String getValue(T object) {
                return "Edit";
            }
        };
        columnEdit.setDataStoreName(COLUMN_EDIT);
        columnEdit.setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int index, T selected, String value) {
                GWT.log(selected.getName() + " pressed");
//                itemEditor.setSelected(selected);
                presenter.setSelectedItem(selected);
            }
        });
        return columnEdit;
    }

    protected Column<T, String> buildColumnDelete() {
        Column<T, String> columnDelete = new Column<T, String>(new TextButtonCell()) {
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
                boolean confirm = Window.confirm("Are you sure?");
                if (confirm) presenter.deleteItem(object);
            }
        });
        return columnDelete;
    }
}
