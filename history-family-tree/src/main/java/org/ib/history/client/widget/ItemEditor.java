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

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
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
        tbName.setText(selectedItem.getName());
        defaultLocaleRow.add(tbName);

        defaultLocaleRow.addAll(getDefaultLocaleWidgets());

        flexTableHandler.addWidgetRow(defaultLocaleRow);

        // locales
        for (SupportedLocale locale : SupportedLocale.getLocalesExceptDefault()) {
            List<Widget> localeRow = new ArrayList<Widget>();

            localeRow.add(new Label(locale.name()));

            T selectedItemLocale = selectedItem.getLocale(locale.name());

            tbName = new TextBox();
            tbName.setText(selectedItemLocale != null ? selectedItem.getLocale(locale.name()).getName() : "");
            localeRow.add(tbName);

            localeRow.addAll(getLocaleWidgets(locale));

            flexTableHandler.addWidgetRow(localeRow);
        }
    }

    protected abstract List<String> getHeaders();
    protected abstract List<Widget> getDefaultLocaleWidgets();
    protected abstract List<Widget> getLocaleWidgets(SupportedLocale locale);


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

    }

    @UiHandler("btnSave")
    public void saveItem(ClickEvent clickEvent) {

    }
}