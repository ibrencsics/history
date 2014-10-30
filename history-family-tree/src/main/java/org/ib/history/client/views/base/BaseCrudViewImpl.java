package org.ib.history.client.views.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.commons.data.AbstractData;


public abstract class BaseCrudViewImpl<T extends AbstractData<T>> extends Composite implements CrudView<T> {

    public static final int NUM_OF_ROWS = 20;

    interface BaseCrudUiBinder extends UiBinder<Widget, BaseCrudViewImpl> {}
    private static BaseCrudUiBinder uiBinder = GWT.create(BaseCrudUiBinder.class);

    @UiField(provided = true)
    protected BaseEditor<T> itemEditor;

    protected BaseList<T> itemLister;
    @UiField(provided = true)
    protected CellTable<T> itemList;

    @UiField(provided = true)
    protected SimplePager pager;

    protected CrudPresenter<T> presenter;


    public BaseCrudViewImpl() {
        itemLister = getItemLister();
        itemList = itemLister.getCtList();

        pager = new SimplePager();
        pager.setDisplay(itemList);
        pager.setPageSize(NUM_OF_ROWS);

        itemEditor = getItemEditor();

        initWidget(uiBinder.createAndBindUi(this));

        Window.enableScrolling(false);
    }

    protected abstract BaseList<T> getItemLister();
    protected abstract BaseEditor<T> getItemEditor();

    /**
     * CrudView methods
     */

    @Override
    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
        itemLister.setPresenter(presenter);
        itemEditor.setPresenter(presenter);
        ((AsyncDataProvider<T>)this.presenter).addDataDisplay(itemList);
    }

    @Override
    public void setSelectedItem(T selectedItem) {
        itemEditor.setSelected(selectedItem);
        itemEditor.setVisible(true);
        itemList.setVisible(false);
    }

    @Override
    public void refreshGrid() {
        itemList.setVisibleRangeAndClearData(new Range(0, NUM_OF_ROWS), true);
        itemList.redraw();
        itemList.setVisible(true);
        itemEditor.hide();
    }
}
