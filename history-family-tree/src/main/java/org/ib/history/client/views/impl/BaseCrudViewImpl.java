package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.CrudView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.AbstractData;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseCrudViewImpl<T extends AbstractData<T>> extends Composite implements CrudView<T> {

    interface BaseCrudUiBinder extends UiBinder<Widget, BaseCrudViewImpl> {}
    private static BaseCrudUiBinder uiBinder = GWT.create(BaseCrudUiBinder.class);

    @UiField(provided = true)
    protected ItemEditor<T> itemEditor;

    @UiField
    protected AbsolutePanel customPanel;

    @UiField(provided = true)
    protected CellTable<T> ctList;

    @UiField(provided = true)
    protected SimplePager pager;

    protected CrudPresenter<T> presenter;

    protected final String COLUMN_NAME = "Name";
    protected final String COLUMN_EDIT = "Edit";
    protected final String COLUMN_DELETE = "Delete";

    public BaseCrudViewImpl() {
        ctList = new CellTable<T>();

        pager = new SimplePager();
        pager.setDisplay(ctList);

        itemEditor = getItemEditor();

        initWidget(uiBinder.createAndBindUi(this));

        buildListTable();

        Window.enableScrolling(false);
    }

    protected abstract ItemEditor<T> getItemEditor();
    protected abstract void notifyCustomPanel(T selected);

    /**
     * CrudView methods
     */

    @Override
    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
        itemEditor.setPresenter(presenter);
        ((AsyncDataProvider<T>)this.presenter).addDataDisplay(ctList);
    }

    @Override
    public void refreshGrid() {
        ctList.setVisibleRangeAndClearData(new Range(0, 25), true);
        ctList.redraw();
        itemEditor.hide();
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
                itemEditor.setSelectedItem(selected);
                notifyCustomPanel(selected);
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

    /**
     * ItemEditor default implementation
     */

    protected abstract class DefaultItemEditorImpl extends ItemEditor<T> {

        @Override
        protected abstract T getEmptyItem();

        @Override
        protected List<String> getHeaders() {
            return new ArrayList<String>(0);
        }

        @Override
        protected List<Widget> getDefaultLocaleWidgets() {
            return new ArrayList<Widget>(0);
        }

        @Override
        protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
            return new ArrayList<Widget>(0);
        }

        @Override
        protected void updateDefaultLocale(List<Widget> widgets) {

        }

        @Override
        protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {

        }
    }

    /**
     * Custom panel
     */

    protected void addCustomPanel(Widget widget) {
        customPanel.clear();
        customPanel.add(widget);
    }
}
