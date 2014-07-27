package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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

import java.util.Map;

public abstract class BaseCrudViewImpl<T extends AbstractData<T>> extends Composite implements CrudView<T> {

    interface BaseCrudUiBinder extends UiBinder<Widget, BaseCrudViewImpl> {}
    private static BaseCrudUiBinder uiBinder = GWT.create(BaseCrudUiBinder.class);

    @UiField(provided = true)
    protected CellTable<T> ctEdit;

    @UiField(provided = true)
    protected CellTable<T> ctLocaleEdit;

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

    protected T selected;

    protected final String COLUMN_NAME = "Name";
    protected final String COLUMN_EDIT = "Edit";
    protected final String COLUMN_DELETE = "Delete";
    protected final String COLUMN_SAVE = "Save";


    public BaseCrudViewImpl() {
        ctEdit = new CellTable<T>();
        ctLocaleEdit = new CellTable<T>();
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
            public void update(int index, T object, String value) {
                GWT.log(object.getName() + " pressed");
                setSelected(object);
            }
        });
        return columnEdit;
    }

    private void setSelected(T selected) {
        localeProvider.getList().clear();
        localeProvider.getList().add(selected);
        for (T locale: selected.getLocales().values()) {
            localeProvider.getList().add(locale);
        }

        this.selected = selected;
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

    /**
     * Editable
     */

    protected Column<T, String> buildEditableColumnName() {
        Column<T, String> columnName = new Column<T, String>(new EditTextCell()) {
            @Override
            public String getValue(T t) {
                return t.getName();
            }
        };
        columnName.setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int i, T t, String s) {
                GWT.log("updated: " + s);
                t.setName(s);
            }
        });
        columnName.setDataStoreName(COLUMN_NAME);
        return columnName;
    }

    protected Column<T, String> buildEditableColumnDelete() {
        Column<T, String> columnDelete = new Column<T, String>(new TextButtonCell()) {
            @Override
            public String getValue(T object) {
                return COLUMN_DELETE;
            }
        };
        columnDelete.setDataStoreName(COLUMN_DELETE);
        columnDelete.setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int index, T object, String value) {
                GWT.log(object.getName() + " pressed");
                selected.removeLocale(object);
//                setSelected(selected);
            }
        });
        return columnDelete;
    }

    protected Column<T, String> buildEditableColumnSave() {
        Column<T, String> columnDelete = new Column<T, String>(new TextButtonCell()) {
            @Override
            public String getValue(T object) {
                return COLUMN_SAVE;
            }
        };
        columnDelete.setDataStoreName(COLUMN_SAVE);
        columnDelete.setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int index, T object, String value) {
                GWT.log(object.getName() + " pressed");
                presenter.addItem(object);
            }
        });
        return columnDelete;
    }

    /**
     * Editable Local
     */


    @UiHandler("btnAdd")
    public void addLocale(ClickEvent clickEvent) {

    }

    @UiHandler("btnSave")
    public void saveLocale(ClickEvent clickEvent) {

    }

    protected void setAddItemForm(Widget addItemForm) {
//        addItemPanel.add(addItemForm);
    }
}
