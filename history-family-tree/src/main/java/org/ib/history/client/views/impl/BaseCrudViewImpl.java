package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.views.CrudView;
import org.ib.history.commons.data.AbstractData;

public abstract class BaseCrudViewImpl<T extends AbstractData> extends Composite implements CrudView<T> {

    interface BaseCellTableUiBinder extends UiBinder<Widget, BaseCrudViewImpl> {}
    private static BaseCellTableUiBinder uiBinder = GWT.create(BaseCellTableUiBinder.class);

    @UiField(provided = true)
    protected CellTable<T> ctEdit;

    @UiField
    Button btnAdd;

    @UiField
    Button btnSave;

    @UiField(provided = true)
    protected CellTable<T> ctList;

    @UiField(provided = true)
    SimplePager pager;

    protected ListDataProvider<T> localeProvider;
    protected CrudPresenter<T> presenter;

    protected final String COLUMN_NAME = "Name";
    protected final String COLUMN_EDIT = "Edit";
    protected final String COLUMN_DELETE = "Delete";


    public BaseCrudViewImpl() {
        ctEdit = new CellTable<T>();
        ctList = new CellTable<T>();

        pager = new SimplePager();
        pager.setDisplay(ctList);

        initWidget(uiBinder.createAndBindUi(this));
//        ctList.setSize("100%", "100%");
//        this.setSize("100%", "100%");

        localeProvider = new ListDataProvider<T>();
        localeProvider.addDataDisplay(ctEdit);

        buildEditTable();
        buildListTable();

        Window.enableScrolling(false);
    }

    /**
     * CrudView methods
     */

    @Override
    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
        ((AsyncDataProvider<T>)this.presenter).addDataDisplay(ctList);
    }

    @Override
    public void refreshGrid() {
        ctList.setVisibleRangeAndClearData(new Range(0, 25), true);
        ctList.redraw();
    }

    /**
     * GUI
     */

    private void buildEditTable() {
        buildEditColumns();
    }

    private void buildListTable() {
        buildListColumns();
    }

    protected abstract void buildEditColumns();
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
            public void update(int index, T object, String value) {
                GWT.log(object.getName() + " pressed");
                localeProvider.getList().clear();
                localeProvider.getList().add(object);
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
                presenter.deleteItem(object);
            }
        });
        return columnDelete;
    }


    protected void setAddItemForm(Widget addItemForm) {
//        addItemPanel.add(addItemForm);
    }
}
