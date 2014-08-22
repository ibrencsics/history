package org.ib.history.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.commons.data.AbstractData;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemEditor<T extends AbstractData<T>> extends Composite implements IsWidget, HasText {

    private static ItemEditorUiBinder uiBinder = GWT.create(ItemEditorUiBinder.class);

    interface ItemEditorUiBinder extends UiBinder<Widget, ItemEditor> {
    }

    @UiField
    Label title;

    @UiField
    protected FlexTable flexTable;

    @UiField
    Button btnNew;

    @UiField
    Button btnSave;

    protected T selectedItem;
    protected CrudPresenter<T> presenter;
    protected FlexTableHandler flexTableHandler;

    public ItemEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * Visualize
     */

    public void hide() {
        flexTable.removeAllRows();
        selectedItem = null;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        GWT.log(selectedItem.toString());
        visualize();
    }

    public void createNewItem(T emptyNewItem) {
        this.selectedItem = emptyNewItem;
        visualize();
    }

    private void visualize() {
        flexTableHandler = new FlexTableHandler();

        // headers
        List<String> headers = new ArrayList<String>(5);
        headers.add("Lang");
        headers.add("Name");
        headers.addAll(getHeaders());
        flexTableHandler.addStringRow(headers);

        // default locale
        List<Widget> defaultLocaleRow = new ArrayList<Widget>(5);
        defaultLocaleRow.add(new Label(SupportedLocale.getDefault().name()));

        TextBox tbName = new TextBox();
        tbName.setText(selectedItem!=null ? selectedItem.getName() : "");
        defaultLocaleRow.add(tbName);

        defaultLocaleRow.addAll(getDefaultLocaleWidgets());

        flexTableHandler.addWidgetRow(defaultLocaleRow);

        // locales
        for (SupportedLocale locale : SupportedLocale.getLocalesExceptDefault()) {
            List<Widget> localeRow = new ArrayList<Widget>();

            localeRow.add(new Label(locale.name()));

            T selectedItemLocale = selectedItem!=null ? selectedItem.getLocale(locale.name()) : null;

            tbName = new TextBox();
            tbName.setText(selectedItemLocale != null ? selectedItem.getLocale(locale.name()).getName() : "");
            localeRow.add(tbName);

            localeRow.addAll(getLocaleWidgets(locale));

            flexTableHandler.addWidgetRow(localeRow);
        }
    }

    protected abstract T getEmptyItem();
    protected abstract List<String> getHeaders();
    protected abstract List<Widget> getDefaultLocaleWidgets();
    protected abstract List<Widget> getLocaleWidgets(SupportedLocale locale);

    /**
     * Save
     */

    private void save() {

        // default locale
        List<Widget> defaultLocale = flexTableHandler.getRow(1);
        selectedItem.setName( ((TextBox)defaultLocale.get(1)).getText() );
        updateDefaultLocale(defaultLocale.subList(2, defaultLocale.size()));

        // locales
        int localeNum = 2;
        for (SupportedLocale locale : SupportedLocale.getLocalesExceptDefault()) {
            List<Widget> localeWidgets = flexTableHandler.getRow(localeNum++);
            TextBox tbName = ((TextBox)localeWidgets.get(1));
            if (tbName.getText()!=null && !tbName.getText().equals("")) {
                if (selectedItem.getLocale(locale.name()) == null) {
                    selectedItem.addLocale(locale.name(), getEmptyItem());
                }
                selectedItem.getLocale(locale.name()).setName( tbName.getText() );
                updateLocale(locale, localeWidgets.subList(2, localeWidgets.size()));
            }
        }

        GWT.log("item to save" + selectedItem.toString());
        presenter.addItem(selectedItem);
    }

    protected abstract void updateDefaultLocale(List<Widget> widgets);
    protected abstract void updateLocale(SupportedLocale locale, List<Widget> widgets);


    private class FlexTableHandler {
        private int nextRow = 0;

        private FlexTableHandler() {
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



    public void setPresenter(CrudPresenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getText() {
        return title.getText();
    }

    @Override
    public void setText(String text) {
        title.setText(text);
    }

    @UiHandler("btnNew")
    public void newItem(ClickEvent clickEvent) {
        createNewItem(getEmptyItem());
    }

    @UiHandler("btnSave")
    public void saveItem(ClickEvent clickEvent) {
        save();
    }
}