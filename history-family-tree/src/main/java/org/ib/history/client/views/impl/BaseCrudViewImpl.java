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
import org.ib.history.commons.data.CountryData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class BaseCrudViewImpl<T extends AbstractData<T>> extends Composite implements CrudView<T> {

    interface BaseCrudUiBinder extends UiBinder<Widget, BaseCrudViewImpl> {}
    private static BaseCrudUiBinder uiBinder = GWT.create(BaseCrudUiBinder.class);

    @UiField(provided = true)
    protected CellTable<LocaleWrapper<T>> ctEdit;

    @UiField
    Button btnNew;

    @UiField
    Button btnAdd;

    @UiField
    Button btnSave;

    @UiField(provided = true)
    protected CellTable<T> ctList;

    @UiField(provided = true)
    SimplePager pager;

    protected ListDataProvider<LocaleWrapper<T>> localeProvider;
    protected CrudPresenter<T> presenter;

//    protected T selected;

    protected final String COLUMN_NAME = "Name";
    protected final String COLUMN_EDIT = "Edit";
    protected final String COLUMN_DELETE = "Delete";
    protected final String COLUMN_SAVE = "Save";
    protected final String COLUMN_LANG = "Lang";


    public BaseCrudViewImpl() {
        ctEdit = new CellTable<LocaleWrapper<T>>();
        ctList = new CellTable<T>();

        pager = new SimplePager();
        pager.setDisplay(ctList);

        initWidget(uiBinder.createAndBindUi(this));
//        ctList.setSize("100%", "100%");
//        this.setSize("100%", "100%");

        localeProvider = new ListDataProvider<LocaleWrapper<T>>();
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
        localeProvider.getList().add( new LocaleWrapper<T>("EN", selected) );
        for (Map.Entry<String, T> entry: selected.getLocales().entrySet()) {
            localeProvider.getList().add( new LocaleWrapper<T>(entry.getKey(), entry.getValue()) );
        }

//        this.selected = selected;
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

    protected Column<LocaleWrapper<T>, String> buildEditableColumnLang() {
        final String[] languages = new String[] { "--", "EN", "DE", "HU" };
        SelectionCell langCell = new SelectionCell(Arrays.asList(languages));
        Column<LocaleWrapper<T>, String> categoryColumn = new Column<LocaleWrapper<T>, String>(langCell) {
            @Override
            public String getValue(LocaleWrapper<T> localeWrapper) {
                return localeWrapper.getLocale();
            }
        };
        categoryColumn.setFieldUpdater(new FieldUpdater<LocaleWrapper<T>, String>() {
            @Override
            public void update(int i, LocaleWrapper<T> localeWrapper, String newLang) {
                GWT.log("updated: " + newLang);
                localeWrapper.setLocale(newLang);
            }
        });

        return categoryColumn;
    }

    protected Column<LocaleWrapper<T>, String> buildEditableColumnName() {
        Column<LocaleWrapper<T>, String> columnName = new Column<LocaleWrapper<T>, String>(new EditTextCell()) {
            @Override
            public String getValue(LocaleWrapper<T> t) {
                return t.item.getName();
            }
        };
        columnName.setFieldUpdater(new FieldUpdater<LocaleWrapper<T>, String>() {
            @Override
            public void update(int i, LocaleWrapper<T> localeWrapper, String newName) {
                GWT.log("updated(" + localeWrapper.getLocale() + "): " + newName);
                localeWrapper.getItem().setName(newName);
            }
        });
        columnName.setDataStoreName(COLUMN_NAME);
        return columnName;
    }

    protected Column<LocaleWrapper<T>, String> buildEditableColumnDelete() {
        Column<LocaleWrapper<T>, String> columnDelete = new Column<LocaleWrapper<T>, String>(new TextButtonCell()) {
            @Override
            public String getValue(LocaleWrapper<T> object) {
                return COLUMN_DELETE;
            }
        };
        columnDelete.setDataStoreName(COLUMN_DELETE);
        columnDelete.setFieldUpdater(new FieldUpdater<LocaleWrapper<T>, String>() {
            @Override
            public void update(int index, LocaleWrapper<T> object, String value) {
                GWT.log(object.item.getName() + " pressed");
//                selected.removeLocale(object.item);
//                setSelected(selected);
            }
        });
        return columnDelete;
    }

    protected Column<LocaleWrapper<T>, String> buildEditableColumnSave() {
        Column<LocaleWrapper<T>, String> columnDelete = new Column<LocaleWrapper<T>, String>(new TextButtonCell()) {
            @Override
            public String getValue(LocaleWrapper<T> object) {
                return COLUMN_SAVE;
            }
        };
        columnDelete.setDataStoreName(COLUMN_SAVE);
        columnDelete.setFieldUpdater(new FieldUpdater<LocaleWrapper<T>, String>() {
            @Override
            public void update(int index, LocaleWrapper<T> object, String value) {
                GWT.log(object.item.getName() + " pressed");
                presenter.addItem(object.item);
            }
        });
        return columnDelete;
    }

    /**
     * Editable Local
     */

    @UiHandler("btnNew")
    public void newItem(ClickEvent clickEvent) {
        localeProvider.getList().clear();
        localeProvider.getList().add(new LocaleWrapper("EN", new CountryData.Builder().name("").build()));
    }

    @UiHandler("btnAdd")
    public void addLocale(ClickEvent clickEvent) {
        LocaleWrapper<T> selected = ctEdit.getVisibleItem(0);
        GWT.log("selected: " + selected.item.getName());
        localeProvider.getList().add(new LocaleWrapper("--", new CountryData.Builder().name("").build()));
//        selected.item.addLocale("--", selected.item);
//        setSelected(selected.item);
    }

    @UiHandler("btnSave")
    public void saveLocale(ClickEvent clickEvent) {
        List<LocaleWrapper<T>> items = ctEdit.getVisibleItems();
        GWT.log("size: " + items.size());
        T defaultItem = null;
        for (LocaleWrapper<T> item : items) {
            if (item.getLocale().equals("EN")) {
                defaultItem = item.getItem();
                defaultItem.removeLocals();
                break;
            }
        }

        if (defaultItem!=null) {
            for (LocaleWrapper<T> item : items) {
                if (!item.getLocale().equals("EN")) {
                    defaultItem.addLocale(item.getLocale(), item.getItem());
                }
            }

            GWT.log("to save: " + defaultItem);
            presenter.addItem(defaultItem);
        }
    }


    static class LocaleWrapper<T> {
        private String locale;
        private T item;

        LocaleWrapper(String locale, T item) {
            this.locale = locale;
            this.item = item;
        }

        String getLocale() {
            return locale;
        }

        void setLocale(String locale) {
            this.locale = locale;
        }

        T getItem() {
            return item;
        }

        void setItem(T item) {
            this.item = item;
        }
    }
}
